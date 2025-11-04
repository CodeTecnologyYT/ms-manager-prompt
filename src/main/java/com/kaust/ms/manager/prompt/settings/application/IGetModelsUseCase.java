package com.kaust.ms.manager.prompt.settings.application;

import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelsResponse;
import reactor.core.publisher.Mono;

public interface IGetModelsUseCase {

    /**
     * Get all models.
     *
     * @return mono {@link ModelsResponse}
     */
    Mono<ModelsResponse> handle();

}
