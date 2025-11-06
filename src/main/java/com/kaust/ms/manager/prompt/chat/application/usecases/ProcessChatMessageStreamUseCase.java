package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGeneratePromptUseCase;
import com.kaust.ms.manager.prompt.chat.application.IProcessChatMessageStreamUseCase;
import com.kaust.ms.manager.prompt.chat.application.ISaveMessageUseCase;
import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
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
     * getChatByIdUseCase.
     */
    private final GetChatByIdUseCase getChatByIdUseCase;

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<String> execute(final String userId, final MessageRequest messageRequest) {
        final var fullResponse = new StringBuilder();
        return getChatByIdUseCase.handle(messageRequest.getChatId(), userId)
                .thenMany(saveMessageUseCase.handle(userId, Role.USER, messageRequest)
                        .thenMany(generatePromptUseCase.handle(messageRequest)
                                .map(word -> {
                                    fullResponse.append(word).append(" ");
                                    return word;
                                })
                                .doOnComplete(() -> messageRequest.setContent(fullResponse.toString().trim()))
                                .concatWith(
                                        Mono.defer(() ->
                                                saveMessageUseCase.handle(userId, Role.ASSISTANT, messageRequest)
                                        ).thenMany(Flux.<String>empty())
                                )));
    }

}
