package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import reactor.core.publisher.Mono;

public interface IGetChatByIdUseCase {

    /**
     * Get chat by id.
     *
     * @param chatId {@link String}
     * @param userId {@link String}
     * @return mono {@link ChatResponse}
     */
    Mono<ChatResponse> handle(String chatId, String userId);

}
