package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.repositories;

import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.ChatDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ChatRepository extends ReactiveMongoRepository<ChatDocument, String> {

    /**
     * Find chat by user id and id.
     *
     * @param userId {@link String}
     * @param id     {@link String}
     * @return {@link Mono}
     */
    Mono<ChatDocument> findByUserIdAndId(String userId, String id);

    /**
     * Find chat by user id and state.
     *
     * @param userId   {@link String}
     * @param state    {@link ChatDocument.State}
     * @param pageable {@link Pageable}
     * @return {@link Flux}
     */
    Flux<ChatDocument> findByUserIdAndStateAndFolderIdIsNullOrderByCreatedAtDesc(String userId, ChatDocument.State state, Pageable pageable);

    /**
     * Count chats by user id and state.
     *
     * @param userId {@link String}
     * @param state  {@link ChatDocument.State}
     * @return mono {@link Long}
     */
    Mono<Long> countByUserIdAndState(String userId, ChatDocument.State state);

    /**
     * Find chats by user id and folder id.
     *
     * @param userId   {@link String}
     * @param folderId {@link String}
     * @param pageable {@link Pageable}
     * @return flux {@link ChatDocument}
     */
    Flux<ChatDocument> findByUserIdAndFolderId(String userId, String folderId, Pageable pageable);

    /**
     * Count chats by user id and folder id.
     *
     * @param userId   {@link String}
     * @param folderId {@link String}
     * @return mono {@link Long}
     */
    Mono<Long> countByUserIdAndFolderId(String userId, String folderId);

    /**
     * Search chat documents by user id, state and title.
     *
     * @param userId {@link String}
     * @param state {@link ChatDocument.State}
     * @param text {@link String}
     * @param pageable {@link Pageable}
     * @return {@link Flux}
     */
    @Query("{'userId' : ?0,'state': ?1 ,'title' : { $regex: ?2, $options: 'i' } }")
    Flux<ChatDocument> searchByUserIdAndStateAndTitle(String userId, ChatDocument.State state, String text, Pageable pageable);

    /**
     * Count chat documents by user id, state and title.
     *
     * @param userId {@link String}
     * @param state {@link ChatDocument.State}
     * @param text {@link String}
     * @return mono {@link Long}
     */
    @Query(value = "{'userId' : ?0,'state': ?1 ,'title' : { $regex: ?2, $options: 'i' } }", count = true)
    Mono<Long> countSearchByUserIdAndStateAndTitle(String userId, ChatDocument.State state, String text);

}
