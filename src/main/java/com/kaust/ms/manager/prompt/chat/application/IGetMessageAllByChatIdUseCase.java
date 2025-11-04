package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.MessageResponse;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface IGetMessageAllByChatIdUseCase {

    /**
     * Get all messages by chat id.
     *
     * @param userId {@link String}
     * @param chatId {@link String}
     * @param pageable {@link Pageable}
     * @return mono of page {@link MessageResponse}
     */
    Mono<PageResponse<MessageResponse>> handle(String userId , String chatId, Pageable pageable);

}
