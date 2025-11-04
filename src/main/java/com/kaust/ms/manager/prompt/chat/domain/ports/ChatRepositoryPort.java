package com.kaust.ms.manager.prompt.chat.domain.ports;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.ChatRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.ChatDocument;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatRepositoryPort {

    /**
     * Save a message.
     *
     * @param chatRequest {@link ChatRequest}
     * @return mono {@link ChatResponse}
     */
    Mono<ChatResponse> save(String userId, ChatRequest chatRequest);

    /**
     * Save a message.
     *
     * @param chatId {@link ChatResponse}
     * @param userId {@link String}
     * @return mono {@link ChatResponse}
     */
    Mono<ChatResponse> findByIdAndByUserId(String chatId, String userId);

    /**
     * Save a message.
     *
     * @param chatId {@link ChatResponse}
     * @param userId {@link String}
     * @return mono {@link ChatDocument}
     */
    Mono<ChatDocument> findById(String chatId, String userId);

    /**
     * List all chats by user id.
     *
     * @param userId   {@link String}
     * @param pageable {@link Pageable}
     * @return flux {@link ChatResponse}
     */
    Flux<ChatResponse> findByUserIdAndStateActive(String userId, Pageable pageable);

    /**
     * Count chats by user id.
     *
     * @param userId {@link String}
     * @return mono {@link Long}
     */
    Mono<Long> countByUserIdAndState(String userId);

    /**
     * List all chats by user id and folder id.
     *
     * @param userId   {@link String}
     * @param folderId {@link String}
     * @param pageable {@link Pageable}
     * @return flux {@link ChatResponse}
     */
    Flux<ChatResponse> findByUserIdAndFolderId(String userId, String folderId,
                                               Pageable pageable);

    /**
     * Count chats by user id and folder id.
     *
     * @param userId   {@link String}
     * @param folderId {@link String}
     * @return mono {@link Long}
     */
    Mono<Long> countByUserIdAndFolderId(String userId, String folderId);

    /**
     * Count chats by user id and folder id.
     *
     * @param folderId     {@link String}
     * @param chatDocument {@link ChatDocument}
     * @return mono {@link ChatResponse}
     */
    Mono<ChatResponse> update(String folderId, ChatDocument chatDocument);

    /**
     * Search chats by user id, state and title.
     *
     * @param userId     {@link String}
     * @param textSearch {@link String}
     * @param pageable   {@link Pageable}
     * @return flux {@link ChatResponse}
     */
    Flux<ChatResponse> searchByUserIdAndStateAndTitle(String userId,
                                                      String textSearch,
                                                      Pageable pageable);

    /**
     * Count chats by user id, state and title.
     *
     * @param userId     {@link String}
     * @param textSearch {@link String}
     * @return mono {@link Long}
     */
    Mono<Long> countSearchByUserIdAndStateAndTitle(String userId,
                                                   String textSearch);

    /**
     * Update model and quantity creative.
     *
     * @param model {@link String}
     * @param quantityCreative {@link Integer}
     * @param chatDocument {@link ChatDocument}
     * @return {@link ChatResponse}
     */
    Mono<ChatResponse> updateModelAndQuantityCreative(final String model, final Integer quantityCreative,
                                                      final ChatDocument chatDocument);

}
