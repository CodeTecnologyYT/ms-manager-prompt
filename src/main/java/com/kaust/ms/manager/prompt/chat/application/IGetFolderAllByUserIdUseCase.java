package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.FolderResponse;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface IGetFolderAllByUserIdUseCase {

    /**
     * Get all folders by user id.
     *
     * @param userId {@link String}
     * @param pageable {@link Pageable}
     * @return {@link Mono}
     */
    Mono<PageResponse<FolderResponse>> getAllByUserId(String userId, Pageable pageable);

}
