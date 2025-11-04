package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGetChatAllByUserIdUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.ChatRepositoryPort;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetChatAllByUserIdUseCase implements IGetChatAllByUserIdUseCase {

    /**
     * chatRepositoryPort.
     */
    private final ChatRepositoryPort chatRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<PageResponse<ChatResponse>> handle(final String userId, final Pageable pageable) {
        final var countTotal = chatRepositoryPort.countByUserIdAndState(userId);

        return chatRepositoryPort.findByUserIdAndStateActive(userId, pageable)
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
