package com.kaust.ms.manager.prompt.chat.infrastructure.ia.adapters;

import com.kaust.ms.manager.prompt.chat.domain.ports.ReactiveRagChatPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ReactiveRagChatAdapter implements ReactiveRagChatPort {

    /* streamChatModel. */
    private final StreamingChatModel streamChatModel;

    private final EmbeddingModel embeddingModel;

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<ChatResponse> ask(final String context) {
        System.out.println("[ReactiveRagChatAdapter][handle][KAUST_FINOK]" + context);
        return Mono.just(prompt(context))
                .flatMapMany(prompt -> streamChatModel.stream(new Prompt(systemMessage(), new UserMessage(prompt)))
                        .filter(chatResponse -> Objects.nonNull(chatResponse.getResult()))
                        .mapNotNull(chatResponse -> chatResponse));
    }

    /**
     * Get prompt from RAG API.
     *
     * @param context  {@link String}
     * @return {@link String}
     */
    private static String prompt(final String context) {
        return """
                the response must be of same the context not agree text additional,it must be in format markdown
                Context: %s""".formatted(context);
    }

    private static SystemMessage systemMessage() {
        return new SystemMessage("""
                You must NEVER split words into separate lines.
                You must NEVER output one token per line.
                Always output full sentences and full paragraphs as normal Markdown.
                All Markdown must be compact and continuous, never fragmented.
                NEVER insert random line breaks between words.
                """);
    }

}
