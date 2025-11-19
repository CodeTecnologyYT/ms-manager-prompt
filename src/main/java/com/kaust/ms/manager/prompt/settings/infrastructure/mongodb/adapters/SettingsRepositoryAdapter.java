package com.kaust.ms.manager.prompt.settings.infrastructure.mongodb.adapters;

import com.kaust.ms.manager.prompt.settings.domain.enums.Model;
import com.kaust.ms.manager.prompt.settings.domain.models.request.ModelGlobalRequest;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelGlobalResponse;
import com.kaust.ms.manager.prompt.settings.domain.ports.SettingsRepositoryPort;
import com.kaust.ms.manager.prompt.settings.infrastructure.mappers.ToSettingsDocumentMapper;
import com.kaust.ms.manager.prompt.settings.infrastructure.mappers.ToSettingsResponseMapper;
import com.kaust.ms.manager.prompt.settings.infrastructure.mongodb.documents.SettingsDocument;
import com.kaust.ms.manager.prompt.settings.infrastructure.mongodb.repositories.CustomRepository;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptError;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class SettingsRepositoryAdapter implements SettingsRepositoryPort {

    /**
     * customRepository.
     */
    private final CustomRepository customRepository;
    /**
     * toCustomResponseMapper.
     */
    private final ToSettingsResponseMapper toSettingsResponseMapper;
    /**
     * toCustomDocumentMapper.
     */
    private final ToSettingsDocumentMapper toSettingsDocumentMapper;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ModelGlobalResponse> findByUserId(final String userId) {
        return customRepository.findFirstByUserIdOrderByCreatedAtDesc(userId)
                .switchIfEmpty(Mono.just(SettingsDocument.builder()
                                .model(Model.AUTO)
                                .quantityCreativity(1)
                                .userId(userId)
                        .build()))
                .map(toSettingsResponseMapper::transformCustomizeDocumentToCustomizeResponse);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<SettingsDocument> findByUserIdToDocument(final String userId) {
        return customRepository.findFirstByUserIdOrderByCreatedAtDesc(userId)
                .switchIfEmpty(Mono.error(new ManagerPromptException(ManagerPromptError.ERROR_SETTINGS_NOT_FOUND, HttpStatus.NOT_FOUND.value())));
    }


    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ModelGlobalResponse> save(final String userId) {
        return customRepository.findFirstByUserIdOrderByCreatedAtDesc(userId)
                .flatMap(existingConfiguration -> {
                    if (existingConfiguration != null) {
                        return Mono.error(new ManagerPromptException(ManagerPromptError.ERROR_SETTINGS_EXIST, HttpStatus.CONFLICT.value()));
                    }
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.defer(() -> customRepository.save(toSettingsDocumentMapper.transformToStartedSettings(userId))
                        .map(toSettingsResponseMapper::transformCustomizeDocumentToCustomizeResponse)))
                .cast(ModelGlobalResponse.class);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ModelGlobalResponse> update(final String userId,
                                            final SettingsDocument settingsDocument,
                                            final ModelGlobalRequest modelGlobalRequest) {
        return customRepository.save(toSettingsDocumentMapper
                        .transformSettingsRequestToSettingsDocument(userId, settingsDocument, modelGlobalRequest))
                .map(toSettingsResponseMapper::transformCustomizeDocumentToCustomizeResponse);
    }


}
