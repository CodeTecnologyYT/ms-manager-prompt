package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.adapters;

import com.kaust.ms.manager.prompt.chat.domain.models.requests.FolderRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.FolderResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.FolderRepositoryPort;
import com.kaust.ms.manager.prompt.chat.infrastructure.mappers.ToFolderDocumentMapper;
import com.kaust.ms.manager.prompt.chat.infrastructure.mappers.ToFolderResponseMapper;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.FolderDocument;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.repositories.FolderRepository;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptError;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class FolderRepositoryAdapter implements FolderRepositoryPort {

    /**
     * folderRepository.
     */
    private final FolderRepository folderRepository;
    /**
     * toFolderDocumentMapper.
     */
    private final ToFolderDocumentMapper toFolderDocumentMapper;
    /**
     * toFolderResponseMapper.
     */
    private final ToFolderResponseMapper toFolderResponseMapper;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<FolderResponse> save(final String userId, final FolderRequest folderRequest) {
        return folderRepository.save(toFolderDocumentMapper
                        .transformFolderRequestToFolderDocument(folderRequest, userId))
                .map(toFolderResponseMapper::transformFolderDocumentToFolderResponse);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<FolderResponse> findAllByUserId(final String userId, final Pageable pageable) {
        return folderRepository.findByUserIdAndState(userId, FolderDocument.State.ACTIVE, pageable)
                .map(toFolderResponseMapper::transformFolderDocumentToFolderResponse);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<Long> countByUserId(final String userId) {
        return folderRepository.countByUserIdAndState(userId, FolderDocument.State.ACTIVE);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<FolderDocument> findById(final String folderId, final String userId) {
        return folderRepository.findByUserIdAndId(userId, folderId)
                .switchIfEmpty(Mono.error(new ManagerPromptException(ManagerPromptError.ERROR_FOLDER_NOT_FOUND, HttpStatus.NOT_FOUND.value())));
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<FolderResponse> update(final FolderDocument folderDocument,
                                       final FolderRequest folderRequest) {
        final var folderUpdate = toFolderDocumentMapper
                .transformFolderRequestAndFolderDocumentToFolderDocument(folderDocument, folderRequest);
        return folderRepository.save(folderUpdate)
                .map(toFolderResponseMapper::transformFolderDocumentToFolderResponse);
    }

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<Void> delete(final FolderDocument folderDocument) {
        folderDocument.setState(FolderDocument.State.DELETE);
        return folderRepository.save(folderDocument)
                .thenEmpty(Mono.empty());
    }

}
