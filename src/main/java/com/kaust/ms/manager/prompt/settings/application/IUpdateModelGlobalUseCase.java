package com.kaust.ms.manager.prompt.settings.application;

import com.kaust.ms.manager.prompt.settings.domain.models.request.ModelGlobalRequest;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelGlobalResponse;
import reactor.core.publisher.Mono;

public interface IUpdateModelGlobalUseCase {

    /**
     * Custom update.
     *
     * @param userId {@link String}
     * @param modelGlobalRequest {@link ModelGlobalRequest}
     * @return mono {@link ModelGlobalResponse}
     */
    Mono<ModelGlobalResponse> handle(String userId, ModelGlobalRequest modelGlobalRequest);

}
