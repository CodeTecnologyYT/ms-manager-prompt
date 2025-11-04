package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IUpdateFolderUsecase;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.FolderRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.FolderResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.FolderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateFolderUseCase implements IUpdateFolderUsecase {

    /**
     * folderRepositoryPort.
     */
    private final FolderRepositoryPort folderRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<FolderResponse> handle(final String userId,
                                       final String folderId,
                                       final FolderRequest folderRequest) {
        return folderRepositoryPort.findById(folderId, userId)
                .flatMap(folderDocument -> folderRepositoryPort.update(folderDocument, folderRequest));
    }

}
