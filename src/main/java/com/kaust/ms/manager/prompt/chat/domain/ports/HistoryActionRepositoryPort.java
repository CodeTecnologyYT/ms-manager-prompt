package com.kaust.ms.manager.prompt.chat.domain.ports;

import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.HistoryActionsDocument;
import reactor.core.publisher.Mono;

public interface HistoryActionRepositoryPort {

    /**
     * Save a history action.
     *
     * @param userId {@link String}
     * @param messageId {@link String}
     * @param chatId {@link String}
     * @param folderId {@link String}
     * @param action {@link HistoryActionsDocument.ACTION}
     * @param model {@link String}
     * @param quantityCreativity {@link Integer}
     * @param numberOfTokens {@link Integer}
     * @return {@link Mono}
     */
    Mono<HistoryActionsDocument> save(final String userId,
                                      final String messageId,
                                      final String chatId,
                                      final String folderId,
                                      final HistoryActionsDocument.ACTION action,
                                      final String model,
                                      final Integer quantityCreativity,
                                      final Integer numberOfTokens);

}
