package com.kaust.ms.manager.prompt.settings.infrastructure.mongodb.repositories;

import com.kaust.ms.manager.prompt.settings.infrastructure.mongodb.documents.SettingsDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomRepository extends ReactiveMongoRepository<SettingsDocument, String> {

    /**
     * Find a customizing by user id.
     *
     * @param userId {@link String}
     * @return mono {@link SettingsDocument}
     */

    Mono<SettingsDocument> findFirstByUserIdOrderByCreatedAtDesc(String userId);

}
