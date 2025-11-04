package com.kaust.ms.manager.prompt.chat.application.usecases;

import com.kaust.ms.manager.prompt.chat.application.IGeneratePromptUseCase;
import com.kaust.ms.manager.prompt.chat.application.IProcessChatMessageStreamUseCase;
import com.kaust.ms.manager.prompt.chat.application.ISaveMessageUseCase;
import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProcessChatMessageStreamUseCase implements IProcessChatMessageStreamUseCase {

    /**
     * generatePromptUseCase.
     */
    private final IGeneratePromptUseCase generatePromptUseCase;
    /**
     * saveMessageUseCase.
     */
    private final ISaveMessageUseCase saveMessageUseCase;
    /**
     * getChatByIdUseCase.
     */
    private final GetChatByIdUseCase getChatByIdUseCase;

    /**
     * @inheritDoc.
     */
    @Override
    public Flux<String> execute(final String userId, final MessageRequest messageRequest) {

        //todo: This is not necessary
        final Function<String, Flux<String>> streamCharFunction = (response) -> {
            final var fullResponse = new StringBuilder();
            return streamByChars(response, 1000)
                    .map(palabra -> {
                        fullResponse.append(palabra).append(" ");
                        return palabra;
                    })
                    .doOnComplete(() -> {
                        messageRequest.setContent(fullResponse.toString().trim());
                    })
                    .concatWith(saveMessageUseCase.handle(userId,
                                    Role.ASSISTANT,
                                    messageRequest)
                            .thenReturn("")
                            .flux()
                            .filter(String::isEmpty)
                    );
        };

        //todo: This there is modify to future
        return getChatByIdUseCase.handle(messageRequest.getChatId(), userId)
                .thenMany(saveMessageUseCase.handle(userId, Role.USER, messageRequest)
                        .thenMany(generatePromptUseCase.handle(messageRequest)
                                .flatMapMany(response ->
                                        Flux.defer(() -> streamCharFunction.apply(response)))));
    }

    //todo: This is not necessary
    private static Flux<String> streamByChars(String text, long delayMillis) {
        return Flux.fromArray(text.split(""))
                .delayElements(Duration.ofMillis(delayMillis));
    }

}
