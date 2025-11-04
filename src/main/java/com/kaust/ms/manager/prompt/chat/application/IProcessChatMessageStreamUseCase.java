package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import reactor.core.publisher.Flux;

public interface IProcessChatMessageStreamUseCase {

    /**
     * Execute the orchestrator chat use case.
     *
     * @param userDataId      {@link String}
     * @param messageRequest {@link MessageRequest}
     * @return mono {@link Void}
     */
    Flux<String> execute(String userDataId, MessageRequest messageRequest);

}
