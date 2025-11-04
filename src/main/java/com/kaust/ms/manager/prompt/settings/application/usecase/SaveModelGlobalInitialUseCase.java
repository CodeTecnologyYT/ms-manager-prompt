package com.kaust.ms.manager.prompt.settings.application.usecase;

import com.kaust.ms.manager.prompt.settings.application.ISaveModelGlobalInitialUseCase;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelGlobalResponse;
import com.kaust.ms.manager.prompt.settings.domain.ports.SettingsRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaveModelGlobalInitialUseCase implements ISaveModelGlobalInitialUseCase {

    /** customRepositoryPort. */
    private final SettingsRepositoryPort settingsRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ModelGlobalResponse> handle(String userId) {
        return settingsRepositoryPort.save(userId);
    }

}
