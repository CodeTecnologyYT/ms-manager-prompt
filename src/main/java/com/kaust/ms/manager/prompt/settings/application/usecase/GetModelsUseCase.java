package com.kaust.ms.manager.prompt.settings.application.usecase;

import com.kaust.ms.manager.prompt.settings.application.IGetModelsUseCase;
import com.kaust.ms.manager.prompt.settings.domain.enums.Model;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelsResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
public class GetModelsUseCase implements IGetModelsUseCase {

    /**
     * @inheritDoc.
     */
    public Mono<ModelsResponse> handle() {
        final var modelsName = Arrays.stream(Model.values())
                .map(Model::getName)
                .toList();
        return Mono.just(new ModelsResponse(modelsName));
    }
}
