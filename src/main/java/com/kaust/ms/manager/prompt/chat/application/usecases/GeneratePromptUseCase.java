package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGeneratePromptUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.chat.domain.ports.RAGChatConsumerApiPort;
import com.kaust.ms.manager.prompt.chat.domain.ports.ReactiveRagChatPort;
import com.kaust.ms.manager.prompt.settings.application.IGetModelGlobalByUserIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GeneratePromptUseCase implements IGeneratePromptUseCase {

    /* reactiveRagChatPort. */
    private final ReactiveRagChatPort reactiveRagChatPort;
    /**
     * ragChatConsumerApiPort.
     */
    private final RAGChatConsumerApiPort ragChatConsumerApiPort;
    /**
     * iGetModelsUseCase.
     */
    private final IGetModelGlobalByUserIdUseCase iGetModelGlobalByUserIdUseCase;


    /**
     * @inheritDoc.
     */
    @Override
    public Flux<ChatResponse> handle(final MessageRequest messageRequest, final String userId,
                                     final String modelChat, final Integer modelChatTemperature) {
        return iGetModelGlobalByUserIdUseCase.handle(userId)
                .flatMap(model -> {
                    final var creative = Objects.nonNull(modelChatTemperature) ? modelChatTemperature : model.getQuantityCreativity();
                    return ragChatConsumerApiPort.response(messageRequest.getContent(), creative);
                })
                .flatMapMany(bio -> reactiveRagChatPort.ask(bio.getAnswer()));
    }

}
