package com.kaust.ms.manager.prompt.chat.domain.ports;

import reactor.core.publisher.Flux;

public interface ReactiveRagChatPort {

    /**
     * Chat with RAG.
     *
     * @param question {@link String}
     * @return flux {@link String}
     */
    Flux<String> ask(String question);

}
