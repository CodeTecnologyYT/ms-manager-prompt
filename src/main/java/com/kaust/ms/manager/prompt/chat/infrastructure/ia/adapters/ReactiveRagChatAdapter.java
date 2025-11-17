package com.kaust.ms.manager.prompt.chat.infrastructure.ia.adapters;

import com.google.storage.v2.Object;
import com.kaust.ms.manager.prompt.chat.domain.ports.ReactiveRagChatPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.metadata.EmptyUsage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ReactiveRagChatAdapter implements ReactiveRagChatPort {

    /* streamChatModel. */
    private final StreamingChatModel streamChatModel;
    /* chatModel. */
    private final ChatModel chatModel;

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<ChatResponse> ask(String question) {
        var usageRef = new AtomicReference<Usage>();

        return streamChatModel.stream(new Prompt(question))
//                .doOnNext(chatResponse -> {
//                    if (!(chatResponse.getMetadata().getUsage() instanceof EmptyUsage)) {
//                        usageRef.set(chatResponse.getMetadata().getUsage());
//                    }
//                })
                .filter(chatResponse -> Objects.nonNull(chatResponse.getResult()))
                .mapNotNull(chatResponse -> chatResponse);
//                .doFinally(signalType -> {
//                    var usage = usageRef.get();
//                    if (usage != null) {
//                        System.out.println("🧾 Tokens usados:");
//                        System.out.println("Prompt: " + usage.getPromptTokens());
//                        System.out.println("Completion: " + usage.getCompletionTokens());
//                        System.out.println("Total: " + usage.getTotalTokens());
//                    } else {
//                        System.out.println("⚠️ No se encontró información de tokens (el modelo puede no enviarla en modo stream).");
//                    }
//                });
    }

}
