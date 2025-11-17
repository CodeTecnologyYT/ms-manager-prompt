package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGeneratePromptUseCase;
import com.kaust.ms.manager.prompt.chat.application.IProcessChatMessageStreamUseCase;
import com.kaust.ms.manager.prompt.chat.application.ISaveMessageUseCase;
import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.chat.domain.ports.ChatRepositoryPort;
import com.kaust.ms.manager.prompt.chat.domain.ports.HistoryActionRepositoryPort;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.HistoryActionsDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.metadata.EmptyUsage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProcessChatMessageStreamUseCase implements IProcessChatMessageStreamUseCase {

    /**
     * generatePromptUseCase.
     */
    private final IGeneratePromptUseCase generatePromptUseCase;
    /**
     * saveMessageUseCase.
     */
    private final ISaveMessageUseCase saveMessageUseCase;
    /**
     * chatRepositoryPort.
     */
    private final ChatRepositoryPort chatRepositoryPort;
    /**
     * historyActionRepositoryPort.
     */
    private final HistoryActionRepositoryPort historyActionRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<String> execute(final String userId, final MessageRequest messageRequest) {
        final var fullResponse = new StringBuilder();
        var usageRef = new AtomicReference<Usage>();

        return chatRepositoryPort.findById(messageRequest.getChatId(), userId)
                .flatMapMany(chatDocument ->
                        saveMessageUseCase.handle(userId, Role.USER, messageRequest)
                                .thenMany(
                                        generatePromptUseCase.handle(messageRequest)
                                                .doOnNext(chatResponse -> {
                                                    if (!(chatResponse.getMetadata().getUsage() instanceof EmptyUsage)) {
                                                        usageRef.set(chatResponse.getMetadata().getUsage());
                                                    }
                                                })
                                                .filter(response -> Objects.nonNull(response.getResult()))
                                                .mapNotNull(response -> response.getResult().getOutput().getText())
                                                .filter(Objects::nonNull)
                                                .doOnNext(text -> fullResponse.append(text).append(" "))
                                                .doOnComplete(() -> messageRequest.setContent(fullResponse.toString().trim()))
                                                // aquí convertimos explícitamente el Mono<Void> final en Flux<String> vacío
                                                .concatWith(
                                                        Mono.defer(() ->
                                                                saveMessageUseCase.handle(userId, Role.ASSISTANT, messageRequest)
                                                                        .flatMap(message ->
                                                                                historyActionRepositoryPort.save(
                                                                                        userId,
                                                                                        message.getId(),
                                                                                        message.getChatId(),
                                                                                        chatDocument.getFolderId(),
                                                                                        HistoryActionsDocument.ACTION.TOKEN,
                                                                                        chatDocument.getModel().getName(),
                                                                                        chatDocument.getQuantityCreativity(),
                                                                                        Optional.ofNullable(usageRef.get())
                                                                                                .map(Usage::getTotalTokens)
                                                                                                .orElse(0)
                                                                                )
                                                                        )
                                                                        // devolvemos un Mono<Void> → convertimos a Flux<String> vacío explícito
                                                                        .then(Mono.<String>empty())
                                                        ).flux()
                                                )
                                )
                );
    }

}
