package com.kaust.ms.manager.prompt.chat.infrastructure.web.controllers;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.chat.application.*;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MailRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.GraphResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalGraphResponse;
import com.kaust.ms.manager.prompt.shared.anotations.current_user.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Tag(name = "Message", description = "API Message")
public class MessageController {

    /** iGeneratePDFSharingMessageUseCase. */
    private final IGeneratePDFSharingMessageUseCase iGeneratePDFSharingMessageUseCase;
    /** iSendMailSharingMessageUseCase. */
    private final ISendMailSharingMessageUseCase iSendMailSharingMessageUseCase;
    /** iGenerateGraphEntitiesUseCase. */
    private final IGenerateGraphEntitiesUseCase iGenerateGraphEntitiesUseCase;

    /**
     * Send email sharing chat.
     *
     * @param userData    {@link UserData}
     * @param messageId   {@link String}
     * @param mailRequest {@link MailRequest}
     * @return mono{@link Void}
     */
    @PostMapping("/{messageId}/sharing/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Send email sharing message", description = "Send email sharing message.")
    @ApiResponse(responseCode = "204", description = "Success send email sharing message.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<Void> sendEmailSharingChat(@CurrentUser UserData userData,
                                           @PathVariable("messageId") String messageId,
                                           @Valid @RequestBody Mono<MailRequest> mailRequest) {
        return mailRequest.flatMap(request -> iGeneratePDFSharingMessageUseCase.handle(messageId, userData.getUid()).
                flatMap(bytes -> iSendMailSharingMessageUseCase.handle(request.getTo(), userData.getUid(), messageId, bytes)));
    }

    /**
     * Generate Graph for a message.
     *
     * @param userData    {@link UserData}
     * @param messageId   {@link String}
     * @return mono{@link GraphResponse}
     */
    @GetMapping("/{messageId}/graph")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Generate Graph for message", description = "Generate Graph for message.")
    @ApiResponse(responseCode = "200", description = "Success generate Graph for message.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<GraphResponse> generateGraphMessage(@CurrentUser UserData userData,
                                                    @PathVariable("messageId") String messageId) {
        return iGenerateGraphEntitiesUseCase.handle(messageId, userData.getUid());
    }

}
