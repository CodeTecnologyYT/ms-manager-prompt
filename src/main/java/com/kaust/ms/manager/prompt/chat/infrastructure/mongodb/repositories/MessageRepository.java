package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.repositories;

import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.MessageDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MessageRepository extends ReactiveMongoRepository<MessageDocument, String> {

    /**
     * Find messages by chat id.
     *
     * @param chatId   {@link String}
     * @param userId   {@link String}
     * @return {@link Flux}
     */
    Flux<MessageDocument> findByChatIdAndUserId(String chatId, String userId);

    /**
     * Find messages by chat id in pagination.
     *
     * @param chatId   {@link String}
     * @param userId   {@link String}
     * @param pageable {@link Pageable}
     * @return {@link Flux}
     */
    Flux<MessageDocument> findByChatIdAndUserId(String chatId, String userId, Pageable pageable);

    /**
     * Count messages by chat id and user id.
     *
     * @param chatId {@link String}
     * @param userId {link String}
     * @return mono {@link Long}
     */
    Mono<Long> countByChatIdAndUserId(String chatId, String userId);

}
