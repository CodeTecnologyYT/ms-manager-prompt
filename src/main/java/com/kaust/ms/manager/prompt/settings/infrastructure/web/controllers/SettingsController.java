package com.kaust.ms.manager.prompt.settings.infrastructure.web.controllers;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.settings.application.IGetModelGlobalByUserIdUseCase;
import com.kaust.ms.manager.prompt.settings.application.ISaveModelGlobalInitialUseCase;
import com.kaust.ms.manager.prompt.settings.application.IUpdateModelGlobalUseCase;
import com.kaust.ms.manager.prompt.settings.domain.models.request.ModelGlobalRequest;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelGlobalResponse;
import com.kaust.ms.manager.prompt.shared.anotations.current_user.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/custom/configuration")
@RequiredArgsConstructor
@Tag(name = "Settings", description = "API setting")
public class SettingsController {

    /**
     * iGetCustomByUserIdUseCase.
     */
    private final IGetModelGlobalByUserIdUseCase iGetModelGlobalByUserIdUseCase;
    /**
     * iUpdateCustomUseCase.
     */
    private final IUpdateModelGlobalUseCase iUpdateModelGlobalUseCase;
    /**
     * iSaveCustomInitialUseCase.
     */
    private final ISaveModelGlobalInitialUseCase iSaveModelGlobalInitialUseCase;

    /**
     * Get custom configuration.
     *
     * @param userData {@link UserData}
     * @return {@link ModelGlobalResponse}
     */
    @GetMapping
    @Operation(summary = "Get custom configuration", description = "Retrieve custom configuration.")
    @ApiResponse(responseCode = "200", description = "Success get custom configuration.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<ModelGlobalResponse> getCustomByUserId(@CurrentUser UserData userData) {
        return iGetModelGlobalByUserIdUseCase.handle(userData.getUid());
    }

    /**
     * Update custom configuration.
     *
     * @param userData           {@link UserData}
     * @param modelGlobalRequest {@link ModelGlobalRequest}
     * @return {@link ModelGlobalResponse}
     */
    @PutMapping
    @Operation(summary = "Update custom configuration", description = "Update custom configuration.")
    @ApiResponse(responseCode = "200", description = "Success update custom configuration.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<ModelGlobalResponse> updateCustomByUserId(@CurrentUser UserData userData,
                                                          @Valid @RequestBody Mono<ModelGlobalRequest> modelGlobalRequest) {
        return modelGlobalRequest.flatMap(request ->
                iUpdateModelGlobalUseCase.handle(userData.getUid(), request));
    }

    /**
     * Save custom configuration.
     *
     * @param userData {@link UserData}
     * @return {@link ModelGlobalResponse}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save custom configuration", description = "Save custom configuration.")
    @ApiResponse(responseCode = "201", description = "Success save custom configuration.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<ModelGlobalResponse> save(@CurrentUser UserData userData) {
        return iSaveModelGlobalInitialUseCase.handle(userData.getUid());
    }

}
