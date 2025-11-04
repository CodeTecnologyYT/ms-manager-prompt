package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGetMessageAllByChatIdUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.MessageResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.MessageRepositoryPort;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetMessageAllByChatIdUseCase implements IGetMessageAllByChatIdUseCase {

    /**
     * messageRepositoryPort.
     */
    private final MessageRepositoryPort messageRepositoryPort;
    /**
     * getChatByIdUseCase.
     */
    private final GetChatByIdUseCase getChatByIdUseCase;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<PageResponse<MessageResponse>> handle(String userId, String chatId, Pageable pageable) {
        final var countTotal = messageRepositoryPort.countByChatIdAndUserId(chatId, userId);

        return getChatByIdUseCase.handle(chatId, userId)
                .then(messageRepositoryPort.findByChatId(userId, chatId, pageable)
                        .collectList()
                        .zipWith(countTotal)
                        .map(tuple -> new PageResponse<>(
                                tuple.getT1(),
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                tuple.getT2()
                        )));
    }

}
