package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGeneratePromptUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.chat.domain.ports.ReactiveRagChatPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GeneratePromptUseCase implements IGeneratePromptUseCase {

    /* reactiveRagChatPort. */
    private final ReactiveRagChatPort reactiveRagChatPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<ChatResponse> handle(final MessageRequest messageRequest) {
        return reactiveRagChatPort.ask(messageRequest.getContent());
    }

}
