package com.kaust.ms.manager.prompt.settings.infrastructure.web.controllers;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.settings.application.IGetModelGlobalByUserIdUseCase;
import com.kaust.ms.manager.prompt.settings.application.ISaveModelGlobalInitialUseCase;
import com.kaust.ms.manager.prompt.settings.application.IUpdateModelGlobalUseCase;
import com.kaust.ms.manager.prompt.settings.domain.models.request.ModelGlobalRequest;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelGlobalResponse;
import com.kaust.ms.manager.prompt.shared.anotations.current_user.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/custom/configuration")
@RequiredArgsConstructor
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
    public Mono<ModelGlobalResponse> getCustomByUserId(final @CurrentUser UserData userData) {
        return iGetModelGlobalByUserIdUseCase.handle(userData.getUid());
    }

    /**
     * Update custom configuration.
     *
     * @param userData      {@link UserData}
     * @param modelGlobalRequest {@link ModelGlobalRequest}
     * @return {@link ModelGlobalResponse}
     */
    @PutMapping
    public Mono<ModelGlobalResponse> updateCustomByUserId(final @CurrentUser UserData userData,
                                                          final @RequestBody ModelGlobalRequest modelGlobalRequest) {
        return iUpdateModelGlobalUseCase.handle(userData.getUid(), modelGlobalRequest);
    }

    /**
     * Save custom configuration.
     *
     * @param userData {@link UserData}
     * @return {@link ModelGlobalResponse}
     */
    @PostMapping
    public Mono<ModelGlobalResponse> save(final @CurrentUser UserData userData) {
        return iSaveModelGlobalInitialUseCase.handle(userData.getUid());
    }

}
