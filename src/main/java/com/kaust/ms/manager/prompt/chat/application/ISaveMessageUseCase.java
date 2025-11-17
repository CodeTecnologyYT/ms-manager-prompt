package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.MessageResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.MessageDocument;
import reactor.core.publisher.Mono;

public interface ISaveMessageUseCase {

    /**
     * Save a message.
     *
     * @param messageRequest {@link MessageRequest}
     * @return {@link MessageDocument}
     */
    Mono<MessageDocument> handle(String userUid, Role role, MessageRequest messageRequest);

}
