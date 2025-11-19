package com.kaust.ms.manager.prompt.chat.domain.ports;

import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

public interface ReactiveRagChatPort {

    /**
     * Chat with RAG.
     *
     * @param context  {@link String}
     * @return flux {@link ChatResponse}
     */
    Flux<ChatResponse> ask(String context);

}
