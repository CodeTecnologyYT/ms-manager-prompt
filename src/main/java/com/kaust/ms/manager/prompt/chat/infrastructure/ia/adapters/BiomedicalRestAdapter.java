package com.kaust.ms.manager.prompt.chat.infrastructure.ia.adapters;

import com.kaust.ms.manager.prompt.chat.domain.models.responses.GraphResponse;
import com.kaust.ms.manager.prompt.chat.domain.ports.RAGChatConsumerApiPort;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalChatResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalGraphResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mappers.ToBiomedicalRequest;
import com.kaust.ms.manager.prompt.chat.infrastructure.mappers.ToGraphResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents.MessageDocument;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptError;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class BiomedicalRestAdapter implements RAGChatConsumerApiPort {

    /* webClient. */
    private final WebClient webClient;
    /* toBiomedicalRequest. */
    private final ToBiomedicalRequest toBiomedicalRequest;
    /** toGraphResponse. */
    private final ToGraphResponse toGraphResponse;
    /** urlBase. */
    @Value("${ms.manager.prompt.api.url}")
    private String urlBase;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<BiomedicalChatResponse> responseChat(final String question, final Double temperature) {
        final var request = toBiomedicalRequest.transform(question, temperature);
        return webClient.post()
                .uri(urlBase + "/chat")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new ManagerPromptException(ManagerPromptError.ERROR_API_BIOMEDICAL_CONNEXION, HttpStatus.INTERNAL_SERVER_ERROR.value())))
                )
                .bodyToMono(BiomedicalChatResponse.class)
                .timeout(Duration.ofSeconds(30));
    }

    public Mono<GraphResponse> responseGraph(final MessageDocument messages) {
        final var request = toBiomedicalRequest.transform(messages);
        return webClient.post()
                .uri(urlBase + "/graph_visualization")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new ManagerPromptException(ManagerPromptError.ERROR_API_BIOMEDICAL_CONNEXION, HttpStatus.INTERNAL_SERVER_ERROR.value())))
                )
                .bodyToMono(BiomedicalGraphResponse.class)
                .map(toGraphResponse::toGraphResponse)
                .timeout(Duration.ofSeconds(30));
    }

}
