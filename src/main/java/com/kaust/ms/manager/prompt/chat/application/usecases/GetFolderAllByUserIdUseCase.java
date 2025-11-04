package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGetFolderAllByUserIdUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.FolderResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.FolderRepositoryPort;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetFolderAllByUserIdUseCase implements IGetFolderAllByUserIdUseCase {

    /**
     * folderRepositoryPort.
     */
    private final FolderRepositoryPort folderRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<PageResponse<FolderResponse>> getAllByUserId(String userId, Pageable pageable) {
        final var countTotal = folderRepositoryPort.countByUserId(userId);
        return folderRepositoryPort.findAllByUserId(userId, pageable)
                .collectList()
                .zipWith(countTotal)
                .map(tuple -> new PageResponse<>(
                        tuple.getT1(),
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        tuple.getT2()
                ));
    }

}
