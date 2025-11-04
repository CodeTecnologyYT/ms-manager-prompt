package com.kaust.ms.manager.prompt.settings.domain.ports;

import com.kaust.ms.manager.prompt.settings.domain.models.request.ModelGlobalRequest;
import com.kaust.ms.manager.prompt.settings.domain.models.response.ModelGlobalResponse;
import com.kaust.ms.manager.prompt.settings.infrastructure.mongodb.documents.SettingsDocument;
import reactor.core.publisher.Mono;

public interface SettingsRepositoryPort {

    /**
     * Find a custom by user id.
     *
     * @param userId {@link String}
     * @return mono {@link ModelGlobalResponse}
     */
    Mono<ModelGlobalResponse> findByUserId(String userId);

    /**
     * Find a custom by user id.
     *
     * @param userId {@link String}
     * @return mono {@link SettingsDocument}
     */
    Mono<SettingsDocument> findByUserIdToDocument(final String userId);

    /**
     * Create a new custom.
     *
     * @param userId {@link String}
     * @return {@link Mono}
     */
    Mono<ModelGlobalResponse> save(String userId);

    /**
     * Update a customize.
     *
     * @param userId         {@link String}
     * @param settingsDocument {@link SettingsDocument}
     * @param modelGlobalRequest  {@link ModelGlobalRequest}
     * @return {@link Mono}
     */
    Mono<ModelGlobalResponse> update(String userId,
                                     SettingsDocument settingsDocument,
                                     ModelGlobalRequest modelGlobalRequest);

}
