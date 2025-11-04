package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IUpdateChatByModelUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.ModelRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.ChatRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateChatByModelUseCase implements IUpdateChatByModelUseCase {

    /**
     * chatRepositoryPort.
     */
    private final ChatRepositoryPort chatRepositoryPort;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<ChatResponse> handle(final String chatId, final String userId,
                                     final ModelRequest modelRequest) {
        return chatRepositoryPort.findById(chatId, userId)
                .flatMap(chatDocument -> chatRepositoryPort
                        .updateModelAndQuantityCreative(modelRequest.getModel(),
                                modelRequest.getQuantityCreativity(), chatDocument));
    }

}
