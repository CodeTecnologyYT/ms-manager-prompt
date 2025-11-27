package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IDeleteFolderUseCase;
import com.kaust.ms.manager.prompt.chat.domain.ports.FolderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteFolderUseCase implements IDeleteFolderUseCase {

    /** folderRepositoryPort. */
    private final FolderRepositoryPort folderRepositoryPort;

    /**
     * @inheritDoc
     */
    @Override
    public Mono<Void> handle(final String userId, final String folderId) {
        return folderRepositoryPort.findById(folderId, userId)
                        .flatMap(folderRepositoryPort::delete);
    }

}
