package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatMessageResponse;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

public interface IProcessChatMessageStreamUseCase {

    /**
     * Execute the orchestrator chat use case.
     *
     * @param userDataId      {@link String}
     * @param messageRequest {@link MessageRequest}
     * @return mono {@link Void}
     */
    Flux<ChatMessageResponse> execute(String userDataId, MessageRequest messageRequest);

}
