package com.kaust.ms.manager.prompt.chat.infrastructure.web.controllers;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.chat.application.*;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MailRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.GraphResponse;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.MarkdownResponse;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalGraphResponse;
import com.kaust.ms.manager.prompt.shared.anotations.current_user.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Tag(name = "Message", description = "API Message")
public class MessageController {

    /**
     * iGeneratePDFSharingMessageUseCase.
     */
    private final IGeneratePDFSharingMessageUseCase iGeneratePDFSharingMessageUseCase;
    /**
     * iSendMailSharingMessageUseCase.
     */
    private final ISendMailSharingMessageUseCase iSendMailSharingMessageUseCase;
    /**
     * iGenerateGraphEntitiesUseCase.
     */
    private final IGenerateGraphEntitiesUseCase iGenerateGraphEntitiesUseCase;
    /**
     * iGenerateContextViewMDUseCase.
     */
    private final IGenerateContextViewMDUseCase iGenerateContextViewMDUseCase;
    /**
     * iGeneratePDFSharingGraph.
     */
    private final IGeneratePDFSharingGraph iGeneratePDFSharingGraph;
    /**
     * isendMailSharingGraphUseCase.
     */
    private final ISendMailSharingGraphUseCase isendMailSharingGraphUseCase;
    /**
     * FILENAME_GRAPH_SHARING_PDF.
     */
    private final static String FILENAME_GRAPH_SHARING_PDF = "sharing-graph";

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
     * Send email sharing graph.
     *
     * @param userData    {@link UserData}
     * @param messageId   {@link String}
     * @param mailRequest {@link MailRequest}
     * @return mono{@link Void}
     */
    @PostMapping("/{messageId}/graph/sharing/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Send email sharing graph", description = "Send email sharing graph.")
    @ApiResponse(responseCode = "204", description = "Success send email sharing graph.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<Void> sendEmailSharingGraph(@CurrentUser UserData userData,
                                           @PathVariable("messageId") String messageId,
                                           @Valid @RequestBody Mono<MailRequest> mailRequest) {
        return mailRequest.flatMap(request -> iGeneratePDFSharingGraph.handle(messageId, userData.getUid()).
                flatMap(bytes -> isendMailSharingGraphUseCase.handle(request.getTo(), userData.getUid(), messageId, bytes)));
    }

    /**
     * Generate Graph for a message.
     *
     * @param userData  {@link UserData}
     * @param messageId {@link String}
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

    /**
     * Generate Graph for a message.
     *
     * @param userData  {@link UserData}
     * @param messageId {@link String}
     * @return mono{@link DefaultDataBuffer}
     */
    @PostMapping("/{messageId}/graph/sharing/pdf")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Generate PFD Graph for message", description = "Generate PFD Graph for message.")
    @ApiResponse(responseCode = "200", description = "Success generate pdf Graph for message.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<ResponseEntity<Flux<DefaultDataBuffer>>> generatePdfGraphMessage(@CurrentUser UserData userData,
                                                                                 @PathVariable("messageId") String messageId) {
        return iGeneratePDFSharingGraph.handle(messageId, userData.getUid())
                .map(pdfBytes -> {
                    final var buffer = new DefaultDataBufferFactory().wrap(ByteBuffer.wrap(pdfBytes));

                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION,
                                    "attachment; filename=\"" + FILENAME_GRAPH_SHARING_PDF + ".pdf\"")
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(Flux.just(buffer));
                });
    }

    /**
     * Generate Graph for a message.
     *
     * @param userData  {@link UserData}
     * @param messageId {@link String}
     * @return mono{@link GraphResponse}
     */
    @GetMapping("/{messageId}/markdown")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Generate markdown for message", description = "Generate Markdown for message.")
    @ApiResponse(responseCode = "200", description = "Success generate Markdown for message.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<MarkdownResponse> generateMarkdownContextView(@CurrentUser UserData userData,
                                                              @PathVariable("messageId") String messageId) {
        return iGenerateContextViewMDUseCase.handle(userData.getUid(), messageId);
    }

}
