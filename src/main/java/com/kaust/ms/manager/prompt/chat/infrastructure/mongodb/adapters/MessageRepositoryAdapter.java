package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.adapters;

import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.MessageResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.MessageRepositoryPort;
import com.kaust.ms.manager.prompt.chat.infrastructure.mappers.ToMessageDocumentMapper;
import com.kaust.ms.manager.prompt.chat.infrastructure.mappers.ToMessageResponseMapper;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.MessageDocument;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryAdapter implements MessageRepositoryPort {

    /**
     * messageRepository.
     */
    private final MessageRepository messageRepository;
    /**
     * toMessageDocumentMapper.
     */
    private final ToMessageDocumentMapper toMessageDocumentMapper;
    /**
     * toMessageResponseMapper.
     */
    private final ToMessageResponseMapper toMessageResponseMapper;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<MessageDocument> saveMessage(final String userId, final Role role,
                                             final MessageRequest message) {
        return messageRepository.save(toMessageDocumentMapper
                        .transformMessageRequestToMessageDocument(role, userId, message));
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<MessageResponse> findByChatId(final String userId, final String chatId, final Pageable pageable) {
        return messageRepository.findByChatIdAndUserId(chatId, userId, pageable)
                .map(toMessageResponseMapper::transformMessageToMessageResponse);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<MessageResponse> findByChatId(final String userId, final String chatId) {
        return messageRepository.findByChatIdAndUserId(chatId, userId)
                .map(toMessageResponseMapper::transformMessageToMessageResponse);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<Long> countByChatIdAndUserId(final String chatId, final String userId) {
        return messageRepository.countByChatIdAndUserId(chatId, userId);
    }

}
