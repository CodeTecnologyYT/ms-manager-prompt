package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface IGetChatAllByFolderIdUseCase {

    /**
     * Get all chats by folder id.
     *
     * @param userId   {@link String}
     * @param folderId {@link String}
     * @param pageable {@link Pageable}
     * @return {@link Mono}
     */
    Mono<PageResponse<ChatResponse>> handle(String userId, String folderId, Pageable pageable);

}
