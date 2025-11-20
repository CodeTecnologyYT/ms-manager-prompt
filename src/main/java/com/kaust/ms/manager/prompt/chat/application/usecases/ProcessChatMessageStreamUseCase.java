package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGeneratePromptUseCase;
import com.kaust.ms.manager.prompt.chat.application.IProcessChatMessageStreamUseCase;
import com.kaust.ms.manager.prompt.chat.application.ISaveMessageUseCase;
import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatMessageResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.ChatRepositoryPort;
import com.kaust.ms.manager.prompt.chat.domain.ports.HistoryActionRepositoryPort;
import com.kaust.ms.manager.prompt.chat.domain.ports.RAGChatConsumerApiPort;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalChatResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.HistoryActionsDocument;
import com.kaust.ms.manager.prompt.settings.application.IGetModelGlobalByUserIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.metadata.EmptyUsage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
     * ragChatConsumerApiPort.
     */
    private final RAGChatConsumerApiPort ragChatConsumerApiPort;
    /**
     * iGetModelsUseCase.
     */
    private final IGetModelGlobalByUserIdUseCase iGetModelGlobalByUserIdUseCase;

    private final static BiomedicalChatResponse EMPTY_ENTITY = null;

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<ChatMessageResponse> execute(final String userId, final MessageRequest messageRequest) {
        final var fullResponse = new StringBuilder();
        var usageRef = new AtomicReference<Usage>();

        return chatRepositoryPort.findById(messageRequest.getChatId(), userId)
                .flatMapMany(chatDocument ->
                        iGetModelGlobalByUserIdUseCase.handle(userId)
                                .flatMap(model -> {
                                            final var temperature = Objects.nonNull(chatDocument.getQuantityCreativity())
                                                    ? chatDocument.getQuantityCreativity() : model.getQuantityCreativity();
                                            return ragChatConsumerApiPort.responseChat(messageRequest.getContent(), temperature);
                                        }
                                ).flatMapMany(biomedicalResponse ->
                                        saveMessageUseCase.handle(userId, Role.USER, messageRequest, EMPTY_ENTITY)
                                                .thenMany(
                                                        generatePromptUseCase.handle(biomedicalResponse.getAnswer())
                                                                .doOnNext(chatResponse -> {
                                                                    if (!(chatResponse.getMetadata().getUsage() instanceof EmptyUsage)) {
                                                                        usageRef.set(chatResponse.getMetadata().getUsage());
                                                                    }
                                                                })
                                                                .filter(response -> Objects.nonNull(response.getResult()))
                                                                .filter(response -> Objects.nonNull(response.getResult().getOutput()))
                                                                .filter(response -> Objects.nonNull(response.getResult().getOutput().getText()))
                                                                .map(chatResponse -> new ChatMessageResponse(chatResponse.getResult().getOutput().getText()))
                                                                .doOnNext(response -> fullResponse.append(response.getContent()))
                                                                .doOnComplete(() -> messageRequest.setContent(fullResponse.toString()))
                                                                // aquí convertimos explícitamente el Mono<Void> final en Flux<String> vacío
                                                                .concatWith(
                                                                        Mono.defer(() ->
                                                                                saveMessageUseCase.handle(userId, Role.ASSISTANT, messageRequest, biomedicalResponse)
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
                                                                                        .then(Mono.<ChatMessageResponse>empty())
                                                                        ).flux()
                                                                )
                                                )
                                )
                );
    }

}
