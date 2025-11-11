package com.kaust.ms.manager.prompt.settings.infrastructure.web.controllers;

import com.kaust.ms.manager.prompt.settings.application.IGetModelsUseCase;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/models")
@RequiredArgsConstructor
@Tag(name = "Model", description = "API Model")
public class ModelController {

    /* iGetModelsUseCase. */
    private final IGetModelsUseCase iGetModelsUseCase;

    /**
     * Get all models.
     *
     * @return {@link ModelsResponse}
     */
    @GetMapping
    @Operation(summary = "Get all models.")
    @ApiResponse(responseCode = "200", description = "Retrieve success get all models")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<ModelsResponse> getModels() {
        return iGetModelsUseCase.handle();
    }

}
