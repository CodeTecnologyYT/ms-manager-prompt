package com.kaust.ms.manager.prompt.chat.domain.ports;

import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.MessageResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.MessageDocument;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MessageRepositoryPort {

    /**
     * Save a message.
     *
     * @param role {@link Role}
     * @param message {@link MessageRequest}
     * @param entities {@link BiomedicalResponse.Entity}
     * @return {@link MessageDocument}
     */
    Mono<MessageDocument> saveMessage(String userId,
                                      Role role,
                                      MessageRequest message,
                                      List<BiomedicalResponse.Entity> entities);

    /**
     * Find messages by user id.
     *
     * @param userId {@link String}
     * @param chatId {@link String}
     * @param pageable {@link Pageable}
     * @return flux {@link MessageResponse}
     */
    Flux<MessageResponse> findByChatId(final String userId, final String chatId, final Pageable pageable);

    /**
     * Find messages by user id.
     *
     * @param userId {@link String}
     * @param chatId {@link String}
     * @return flux {@link MessageResponse}
     */
    Flux<MessageResponse> findByChatId(final String userId, final String chatId);

    /**
     * Find message by id.
     *
     * @param userId {@link String}
     * @param messageId {@link String}
     * @return mono {@link MessageResponse}
     */
    Mono<MessageResponse> findById(final String userId, final String messageId);

    /**
     * Count messages by chat id and user id.
     *
     * @param chatId {@link String}
     * @param userId {@link String}
     * @return mono {@link Long}
     */
    Mono<Long> countByChatIdAndUserId(final String chatId, final String userId);

}
