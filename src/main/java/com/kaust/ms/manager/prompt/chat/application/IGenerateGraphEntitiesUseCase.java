package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.GraphResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalGraphResponse;
import reactor.core.publisher.Mono;

public interface IGenerateGraphEntitiesUseCase {

    /**
     * Generate graph entities.
     *
     * @param messageId {@link String}
     * @param userId {@link String}
     * @return mono {@link BiomedicalGraphResponse}
     */
    Mono<GraphResponse> handle(final String messageId, final String userId);

}
