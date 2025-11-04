package com.kaust.ms.manager.prompt.settings.application;

import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelGlobalResponse;
import reactor.core.publisher.Mono;

public interface IGetModelGlobalByUserIdUseCase {

    /**
     * Find a custom by user id.
     *
     * @param userId {@link String}
     * @return mono {@link ModelGlobalResponse}
     */
    Mono<ModelGlobalResponse> handle(final String userId);

}
