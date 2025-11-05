package com.kaust.ms.manager.prompt.chat.infrastructure.ia.adapters;

import com.kaust.ms.manager.prompt.chat.domain.ports.ReactiveRagChatPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReactiveRagChatAdapter implements ReactiveRagChatPort {

    /* chatModel. */
    private final StreamingChatModel chatModel;

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<String> ask(String question) {
        return chatModel.stream(new Prompt(question))
                .mapNotNull(chatResponse -> chatResponse.getResult().getOutput().getText());
    }

}
