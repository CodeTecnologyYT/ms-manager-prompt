package com.kaust.ms.manager.prompt.chat.infrastructure.ia.adapters;

import com.kaust.ms.manager.prompt.chat.domain.ports.RAGChatConsumerApiPort;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.mappers.ToBiomedicalRequest;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptError;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RAGChatConsumerAdapter implements RAGChatConsumerApiPort {

    /* webClient. */
    private final WebClient webClient;
    /* toBiomedicalRequest. */
    private final ToBiomedicalRequest toBiomedicalRequest;
    /** urlBase. */
    @Value("${ms.manager.prompt.api.url}")
    private String urlBase;

    /**
     * @inheritDoc.
     */
    @Override
    public Mono<BiomedicalResponse> response(final String question, final Integer temperature) {
        final var request = toBiomedicalRequest.transform(question, temperature);
        return webClient.post()
                .uri(urlBase + "/chat")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new ManagerPromptException(ManagerPromptError.ERROR_API_BIOMEDICAL_CONNEXION, HttpStatus.INTERNAL_SERVER_ERROR.value())))
                )
                .bodyToMono(BiomedicalResponse.class);
    }

}
