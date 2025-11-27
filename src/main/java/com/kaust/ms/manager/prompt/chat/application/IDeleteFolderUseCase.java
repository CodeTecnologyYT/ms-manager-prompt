package com.kaust.ms.manager.prompt.chat.application;

import reactor.core.publisher.Mono;

public interface IDeleteFolderUseCase {

    /**
     * Delete folder.
     *
     * @param userId {@link String}
     * @param folderId {@link String}
     * @return {@link Void}
     */
    Mono<Void> handle(String userId, String folderId);

}
