package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.repositories;

import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.HistoryActionsDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface HistoryActionRepository extends ReactiveMongoRepository<HistoryActionsDocument, String> {
}
