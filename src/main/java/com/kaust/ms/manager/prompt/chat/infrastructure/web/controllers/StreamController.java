package com.kaust.ms.manager.prompt.chat.infrastructure.web.controllers;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.chat.application.IProcessChatMessageStreamUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.shared.anotations.current_user.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stream")
@RequiredArgsConstructor
public class StreamController {

    /**
     * iStartChatFlowUseCase.
     */
    private final IProcessChatMessageStreamUseCase iProcessChatMessageStreamUseCase;

    /**
     * Response stream of chat messages.
     *
     * @param userData       {@link UserData}
     * @param messageRequest {@link MessageRequest}
     * @return flux {@link String}
     */
    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> processChatMessageStream(@CurrentUser UserData userData, @RequestBody MessageRequest messageRequest) {
        return iProcessChatMessageStreamUseCase.execute(userData.getUid(), messageRequest);
    }

}
