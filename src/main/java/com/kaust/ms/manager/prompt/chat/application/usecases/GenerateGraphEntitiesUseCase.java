package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGenerateGraphEntitiesUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.GraphResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.MessageRepositoryPort;
import com.kaust.ms.manager.prompt.chat.domain.ports.RAGChatConsumerApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GenerateGraphEntitiesUseCase implements IGenerateGraphEntitiesUseCase {

    /** ragChatConsumerApiPort. */
    private final RAGChatConsumerApiPort ragChatConsumerApiPort;
    /** messageRepositoryPort. */
    private final MessageRepositoryPort messageRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<GraphResponse> handle(final String messageId, final String userId) {
        return messageRepositoryPort.findByIdToDocument(userId, messageId)
                .flatMap(ragChatConsumerApiPort::responseGraph);
    }

}
