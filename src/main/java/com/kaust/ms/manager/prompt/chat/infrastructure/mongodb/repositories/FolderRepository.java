package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.repositories;

import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.FolderDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FolderRepository extends ReactiveMongoRepository<FolderDocument, String> {

    /**
     * Find all folders by user id.
     *
     * @param userId {@link String}
     * @return flux {@link FolderDocument}
     */
    Flux<FolderDocument> findByUserId(String userId, Pageable pageable);

    /**
     * Count folders by user id.
     *
     * @param userId {@link String}
     * @return mono {@link Long}
     */
    Mono<Long> countByUserId(String userId);

    /**
     * Find folder by user id and id.
     *
     * @param userId {@link String}
     * @param id {@link String}
     * @return mono {@link FolderDocument}
     */
    Mono<FolderDocument> findByUserIdAndId(String userId, String id);

}
