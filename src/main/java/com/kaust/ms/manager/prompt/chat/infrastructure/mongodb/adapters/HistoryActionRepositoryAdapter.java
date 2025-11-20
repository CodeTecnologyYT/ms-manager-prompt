package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.adapters;

import com.kaust.ms.manager.prompt.chat.domain.ports.HistoryActionRepositoryPort;
import com.kaust.ms.manager.prompt.chat.infrastructure.mappers.ToHistoryActionMapper;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.HistoryActionsDocument;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.repositories.HistoryActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class HistoryActionRepositoryAdapter implements HistoryActionRepositoryPort {

    /**
     * historyActionRepository.
     */
    private final HistoryActionRepository historyActionRepository;
    /**
     * toHistoryActionMapper.
     */
    private final ToHistoryActionMapper toHistoryActionMapper;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<HistoryActionsDocument> save(final String userId,
                                             final String messageId,
                                             final String chatId,
                                             final String folderId,
                                             final HistoryActionsDocument.ACTION action,
                                             final String model,
                                             final Double quantityCreativity,
                                             final Integer numberOfTokens) {
        return historyActionRepository.save(toHistoryActionMapper.toHistoryActionDocument(userId, messageId,
                chatId, folderId, action, model, quantityCreativity, numberOfTokens));
    }

}
