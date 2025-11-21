package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGenerateContextViewMDUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.MarkdownResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.MarkdownTemplatePort;
import com.kaust.ms.manager.prompt.chat.domain.ports.MessageRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class GenerateContextViewMDUseCase implements IGenerateContextViewMDUseCase {

    /**
     * markdownTemplatePort.
     */
    private final MarkdownTemplatePort markdownTemplatePort;
    /** messageRepositoryPort. */
    private final MessageRepositoryPort messageRepositoryPort;
    /** PATH_TEMPLATE. */
    private final static String PATH_TEMPLATE = "markdown/context-view.md";


    public Mono<MarkdownResponse> handle(final String userId, final String messageId) {
        return messageRepositoryPort.findByIdToDocument(userId, messageId)
                .map(message -> {
                    final var model = Map.<String, Object>of(
                            "question", message.getMessageUser(),
                            "extractedCandidates", message.getExtractedCandidates(),
                            "entities", message.getEntities(),
                            "unmatchedCandidates", message.getUnmatchedCandidates(),
                            "chunk", message.getChunks(),
                            "chunkTogether", IntStream.range(0, message.getChunks().size())
                                    .takeWhile(i -> i < message.getChunkPatterns().getTogether() && i < 5)
                                    .mapToObj(i -> message.getChunks().get(i))
                                    .collect(Collectors.toList()),
                            "chunkOthers", IntStream.range(0, 4)
                                    .mapToObj(i -> message.getChunks().get(i))
                                    .collect(Collectors.toList()),
                            "chunkPatterns", message.getChunkPatterns(),
                            "triplets", message.getTriplets()
                    );
                    return markdownTemplatePort.process(PATH_TEMPLATE, model);
                });
    }

}
