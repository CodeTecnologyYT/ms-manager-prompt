package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.ISaveFolderUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.FolderRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.FolderResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.FolderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaveFolderUseCase implements ISaveFolderUseCase {

    /** folderRepositoryPort. */
    private final FolderRepositoryPort folderRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<FolderResponse> handle(final String userId, final FolderRequest folderRequest) {
        return folderRepositoryPort.save(userId, folderRequest);
    }

}
