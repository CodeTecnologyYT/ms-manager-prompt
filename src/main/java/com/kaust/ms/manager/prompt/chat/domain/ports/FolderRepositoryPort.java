package com.kaust.ms.manager.prompt.chat.domain.ports;


import com.kaust.ms.manager.prompt.chat.domain.models.requests.FolderRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.FolderResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.FolderDocument;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FolderRepositoryPort {

    /**
     * Save a folder.
     *
     * @param userId        {@link String}
     * @param folderRequest {@link FolderRequest}
     * @return {@link FolderResponse}
     */
    Mono<FolderResponse> save(String userId, FolderRequest folderRequest);

    /**
     * Find all folders by user id.
     *
     * @param userId   {@link String}
     * @param pageable {@link Pageable}
     * @return flux {@link FolderResponse}
     */
    Flux<FolderResponse> findAllByUserId(String userId, Pageable pageable);

    /**
     * Count folders by user id.
     *
     * @param userId {@link String}
     * @return mono {@link Long}
     */
    Mono<Long> countByUserId(String userId);

    /**
     * Find a folder by id.
     *
     * @param userId   {@link String}
     * @param folderId {@link String}
     * @return {@link FolderDocument}
     */
    Mono<FolderDocument> findById(String folderId, String userId);

    /**
     * Update folder.
     *
     * @param folderDocument {@link FolderDocument}
     * @param folderRequest  {@link FolderRequest}
     * @return {@link FolderResponse}
     */
    Mono<FolderResponse> update(FolderDocument folderDocument,
                                FolderRequest folderRequest);

}
