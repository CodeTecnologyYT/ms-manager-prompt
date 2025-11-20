package com.kaust.ms.manager.prompt.chat.domain.ports;

import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalResponse;
import reactor.core.publisher.Mono;

public interface RAGChatConsumerApiPort {

    /**
     * Response to a question.
     *
     * @param question    {@link String}
     * @param temperature {@link Double}
     * @return {@link BiomedicalResponse}
     */
    Mono<BiomedicalResponse> response(String question, Double temperature);

}
