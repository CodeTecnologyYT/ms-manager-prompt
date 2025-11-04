package com.kaust.ms.manager.prompt.settings.infrastructure.web.controllers;

import com.kaust.ms.manager.prompt.settings.application.IGetModelsUseCase;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/models")
@RequiredArgsConstructor
public class ModelController {

    /* iGetModelsUseCase. */
    private final IGetModelsUseCase iGetModelsUseCase;

    @GetMapping
    public Mono<ModelsResponse> getModels(){
        return iGetModelsUseCase.handle();
    }

}
