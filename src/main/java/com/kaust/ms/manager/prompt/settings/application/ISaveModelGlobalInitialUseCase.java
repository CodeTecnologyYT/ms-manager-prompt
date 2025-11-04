package com.kaust.ms.manager.prompt.settings.application;

import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelGlobalResponse;
import reactor.core.publisher.Mono;

public interface ISaveModelGlobalInitialUseCase {

    /**
     * Create a custom for the user.
     *
     * @param userId {@link String}
     * @return mono {@link ModelGlobalResponse}
     */
    Mono<ModelGlobalResponse> handle(String userId);

}
