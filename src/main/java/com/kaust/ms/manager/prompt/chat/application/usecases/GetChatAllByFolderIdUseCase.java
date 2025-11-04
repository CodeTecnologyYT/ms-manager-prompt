package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGetChatAllByFolderIdUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.ChatRepositoryPort;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetChatAllByFolderIdUseCase implements IGetChatAllByFolderIdUseCase {

    /** chatRepositoryPort. */
    private final ChatRepositoryPort chatRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<PageResponse<ChatResponse>> handle(String userId,String folderId, Pageable pageable) {
        final var countTotal = chatRepositoryPort.countByUserIdAndFolderId(userId, folderId);
        return chatRepositoryPort.findByUserIdAndFolderId(userId, folderId, pageable)
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
