package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.repositories;

import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.FolderDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FolderRepository extends ReactiveMongoRepository<FolderDocument, String> {

    /**
     * Find all folders by user id.
     *
     * @param userId {@link String}
     * @param state  {@link FolderDocument.State}
     * @param pageable {@link Pageable}
     * @return flux {@link FolderDocument}
     */
    Flux<FolderDocument> findByUserIdAndState(String userId, FolderDocument.State state, Pageable pageable);

    /**
     * Count folders by user id.
     *
     * @param userId {@link String}
     * @param state  {@link FolderDocument.State}
     * @return mono {@link Long}
     */
    Mono<Long> countByUserIdAndState(String userId, FolderDocument.State state);

    /**
     * Find folder by user id and id.
     *
     * @param userId {@link String}
     * @param id     {@link String}
     * @return mono {@link FolderDocument}
     */
    Mono<FolderDocument> findByUserIdAndId(String userId, String id);

    List<FolderDocument> State(FolderDocument.State state);
}
