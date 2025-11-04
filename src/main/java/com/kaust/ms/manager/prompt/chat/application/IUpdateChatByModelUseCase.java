package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.ModelRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import reactor.core.publisher.Mono;

public interface IUpdateChatByModelUseCase {

    /**
     * Update chat by model.
     *
     * @param chatId       {@link String}
     * @param userId       {@link String}
     * @param modelRequest {@link ModelRequest}
     * @return {@link Mono}
     */
    Mono<ChatResponse> handle(final String chatId, final String userId,
                              final ModelRequest modelRequest);

}
