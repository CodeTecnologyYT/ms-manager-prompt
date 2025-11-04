package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.FolderRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.FolderResponse;
import reactor.core.publisher.Mono;

public interface ISaveFolderUseCase {

    /**
     * Save a folder.
     *
     * @param userId {@link String}
     * @param folderRequest {@link FolderRequest}
     * @return {@link FolderResponse}
     */
    Mono<FolderResponse> handle(final String userId, final FolderRequest folderRequest);

}
