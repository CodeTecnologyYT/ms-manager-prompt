package com.kaust.ms.manager.prompt.settings.application.usecase;

import com.kaust.ms.manager.prompt.settings.application.IUpdateModelGlobalUseCase;
import com.kaust.ms.manager.prompt.settings.domain.models.request.ModelGlobalRequest;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelGlobalResponse;
import com.kaust.ms.manager.prompt.settings.domain.ports.SettingsRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateModelGlobalUseCase implements IUpdateModelGlobalUseCase {

    /** customRepositoryPort. */
    private final SettingsRepositoryPort settingsRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ModelGlobalResponse> handle(String userId, ModelGlobalRequest modelGlobalRequest) {
        return settingsRepositoryPort.findByUserIdToDocument(userId)
                .flatMap(customDocument ->
                        settingsRepositoryPort.update(userId, customDocument, modelGlobalRequest));
    }

}
