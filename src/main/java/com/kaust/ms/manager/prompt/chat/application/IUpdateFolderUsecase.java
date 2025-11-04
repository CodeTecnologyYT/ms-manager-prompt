package com.kaust.ms.manager.prompt.chat.application;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.FolderRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.FolderResponse;
import reactor.core.publisher.Mono;

public interface IUpdateFolderUsecase {

    /**
     * Update folder.
     *
     * @param userId   {@link String}
     * @param folderId {@link String}
     * @param folderRequest   {@link FolderRequest}
     * @return mono {@link FolderResponse}
     */
    Mono<FolderResponse> handle(final String userId,
                                final String folderId,
                                final FolderRequest folderRequest);

}
