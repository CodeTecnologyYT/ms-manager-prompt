package com.kaust.ms.manager.prompt.chat.domain.ports;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.GraphResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalChatResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalGraphResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.MessageDocument;
import reactor.core.publisher.Mono;

public interface RAGChatConsumerApiPort {

    /**
     * Response to a question.
     *
     * @param question    {@link String}
     * @param temperature {@link Double}
     * @return {@link BiomedicalChatResponse}
     */
    Mono<BiomedicalChatResponse> responseChat(String question, Double temperature);

    /**
     * Response to a graph.
     *
     * @param messages {@link MessageDocument}
     * @return {@link GraphResponse}
     */
    Mono<GraphResponse> responseGraph(MessageDocument messages);

}
