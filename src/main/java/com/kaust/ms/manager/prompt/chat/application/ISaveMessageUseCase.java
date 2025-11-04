package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import reactor.core.publisher.Mono;

public interface ISaveMessageUseCase {

    /**
     * Save a message.
     *
     * @param messageRequest {@link MessageRequest}
     * @return {@link Void}
     */
    Mono<Void> handle(String userUid, Role role, MessageRequest messageRequest);

}
