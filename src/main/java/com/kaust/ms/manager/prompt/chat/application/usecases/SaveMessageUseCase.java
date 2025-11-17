package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.ISaveMessageUseCase;
import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.MessageResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.MessageRepositoryPort;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.MessageDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaveMessageUseCase implements ISaveMessageUseCase {

    /**
     * messageRepositoryPort.
     */
    private final MessageRepositoryPort messageRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<MessageDocument> handle(final String userUid, final Role role,
                                        final MessageRequest messageRequest) {
        return messageRepositoryPort.saveMessage( userUid, role, messageRequest);
    }

}
