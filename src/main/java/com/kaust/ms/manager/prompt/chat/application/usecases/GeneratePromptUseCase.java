package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGeneratePromptUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GeneratePromptUseCase implements IGeneratePromptUseCase {

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<String> handle(final MessageRequest messageRequest) {
        return Mono.just("Hola Mundo");
    }

}
