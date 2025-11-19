package com.kaust.ms.manager.prompt.chat.infrastructure.web.controllers;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.chat.application.IProcessChatMessageStreamUseCase;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MessageRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatMessageResponse;
import com.kaust.ms.manager.prompt.shared.anotations.current_user.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/stream")
@RequiredArgsConstructor
@Tag(name = "Stream", description = "API stream")
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
    @Operation(summary = "Response stream of chat messages", description = "Response stream of chat messages.")
    @ApiResponse(responseCode = "200", description = "Success response stream of chat messages.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Flux<ChatMessageResponse> processChatMessageStream(@CurrentUser UserData userData, @Valid @RequestBody Mono<MessageRequest> messageRequest) {
        return messageRequest.flatMapMany(request ->
                        iProcessChatMessageStreamUseCase.execute(userData.getUid(), request));
    }



}
