package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import reactor.core.publisher.Mono;

public interface IUpdateChatUseCase {

    /**
     * Update chat.
     *
     * @param userId {@link String}
     * @param folderId {@link String}
     * @param chatId {@link String}
     * @return mono {@link ChatResponse}
     */
    Mono<ChatResponse> handle(String userId, String folderId, String  chatId);

}
