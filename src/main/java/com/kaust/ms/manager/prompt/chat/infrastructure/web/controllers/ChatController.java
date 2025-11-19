package com.kaust.ms.manager.prompt.chat.infrastructure.web.controllers;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.chat.application.*;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.ChatRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MailRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.ModelRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.SearchPromptRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.MessageResponse;
import com.kaust.ms.manager.prompt.shared.anotations.current_user.CurrentUser;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Chat", description = "API chats")
public class ChatController {

    /*
     * iGetChatByUserIdUseCase.
     */
    private final IGetChatAllByUserIdUseCase iGetChatByUserIdUseCase;
    /**
     * iGetChatByIdUseCase.
     */
    private final IGetChatByIdUseCase iGetChatByIdUseCase;
    /**
     * iSaveChatUseCase.
     */
    private final ISaveChatUseCase iSaveChatUseCase;
    /**
     * iGetAllMessageByChatIdUseCase.
     */
    private final IGetMessageAllByChatIdUseCase iGetMessageAllByChatIdUseCase;
    /**
     * iSearchChatUseCase.
     */
    private final ISearchChatUseCase iSearchChatUseCase;
    /**
     * iUpdateChatByModelUseCase.
     */
    private final IUpdateChatByModelUseCase iUpdateChatByModelUseCase;
    /**
     * updateChatUseCase.
     */
    private final IUpdateChatUseCase updateChatUseCase;
    /**
     * iSendMailSharingChatUseCase.
     */
    private final ISendMailSharingChatUseCase iSendMailSharingChatUseCase;
    /* iGeneratePDFSharingChatUseCase. */
    private final IGeneratePDFSharingChatUseCase iGeneratePDFSharingChatUseCase;
    /* NAME_CHAT_SHARING_PDF. */
    private final static String FILENAME_CHAT_SHARING_PDF = "sharing-chat";

    /**
     * Get chat by id.
     *
     * @param userData {@link UserData}
     * @param chatId   {@link String}
     * @return mono {@link ChatResponse}
     */
    @GetMapping("/{chatId}")
    @Operation(summary = "Get chat by id", description = "Retrieve a chats by id and user.")
    @ApiResponse(responseCode = "200", description = "Success get chats by id.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<ChatResponse> getChatById(@CurrentUser UserData userData,
                                          @PathVariable("chatId") String chatId) {
        return iGetChatByIdUseCase.handle(chatId, userData.getUid());
    }

    /**
     * Get all messages by chat id.
     *
     * @param userData {@link UserData}
     * @param page     {@link Integer}
     * @param size     {@link Integer}
     * @return mono of page {@link MessageResponse}
     */
    @GetMapping("/{chatId}/messages")
    @Operation(summary = "Get all message by chat id and user", description = "Retrieve all messages by chat id and user.")
    @ApiResponse(responseCode = "200", description = "Success get messages by chat id.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<PageResponse<MessageResponse>> getAllMessageByChatId(@CurrentUser UserData userData,
                                                                     @PathVariable("chatId") String chatId,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        final var pageable = PageRequest.of(page, size);
        return iGetMessageAllByChatIdUseCase.handle(userData.getUid(), chatId, pageable);
    }

    /**
     * Create chat.
     *
     * @param userData    {@link UserData}
     * @param chatRequest mono {@link ChatRequest}
     * @return mono {@link String}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a chat", description = "Create a chat by user.")
    @ApiResponse(responseCode = "201", description = "Success create a chat by user.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<ChatResponse> createChat(@CurrentUser UserData userData,
                                         @Valid @RequestBody Mono<ChatRequest> chatRequest) {
        return chatRequest.flatMap(request ->
                iSaveChatUseCase.handle(userData.getUid(), request));

    }

    /**
     * Get all chats by user id.
     *
     * @param userData {@link UserData}
     * @return mono of page {@link ChatResponse}
     */
    @GetMapping
    @Operation(summary = "Get all chats", description = "Retrieve all chats by user.")
    @ApiResponse(responseCode = "200", description = "Success get chats by user.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<PageResponse<ChatResponse>> getChatByUserId(@CurrentUser UserData userData,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        final var pageable = PageRequest.of(page, size);
        return iGetChatByUserIdUseCase.handle(userData.getUid(), pageable);
    }

    /**
     * Search chat by text.
     *
     * @param userData {@link UserData}
     * @param search   {@link SearchPromptRequest}
     * @param page     {@link Integer}
     * @param size     {@link Integer}
     * @return mono of page {@link ChatResponse}
     */
    @PostMapping("/search")
    @Operation(summary = "Search chats by text", description = "Retrieve search chats by text.")
    @ApiResponse(responseCode = "200", description = "Success search chats by text.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<PageResponse<ChatResponse>> searchChatByText(@CurrentUser UserData userData,
                                                             @Valid @RequestBody Mono<SearchPromptRequest> search,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        final var pageable = PageRequest.of(page, size);
        return search.flatMap(request ->
                iSearchChatUseCase.handle(userData.getUid(), request.getTextSearch(), pageable));
    }

    /**
     * Update chat model.
     *
     * @param userData     {@link UserData}
     * @param chatId       {@link String}
     * @param modelRequest {@link ModelRequest}
     * @return mono {@link ChatResponse}
     */
    @PutMapping("/{chatId}/model")
    @Operation(summary = "Search chats by text", description = "Retrieve search chats by text.")
    @ApiResponse(responseCode = "200", description = "Success search chats by text.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<ChatResponse> updateChatModel(@CurrentUser UserData userData,
                                              @PathVariable("chatId") String chatId,
                                              @Valid @RequestBody Mono<ModelRequest> modelRequest) {
        return modelRequest.flatMap(request ->
                iUpdateChatByModelUseCase.handle(chatId, userData.getUid(), request));
    }

    /**
     * Send email sharing chat.
     *
     * @param userData    {@link UserData}
     * @param chatId      {@link String}
     * @param mailRequest {@link MailRequest}
     * @return mono{@link Void}
     */
    @PostMapping("/{chatId}/sharing/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Send email sharing chat", description = "Send email sharing chat.")
    @ApiResponse(responseCode = "204", description = "Success send email sharing chat.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<Void> sendEmailSharingChat(@CurrentUser UserData userData,
                                           @PathVariable("chatId") String chatId,
                                           @Valid @RequestBody Mono<MailRequest> mailRequest) {
        return mailRequest.flatMap(request -> iGeneratePDFSharingChatUseCase.handle(chatId, userData.getUid()).
                flatMap(bytes -> iSendMailSharingChatUseCase.handle(request.getTo(), userData.getUid(), chatId, bytes)));
    }

    /**
     * Send email sharing chat.
     *
     * @param userData {@link UserData}
     * @param chatId   {@link String}
     * @return mono{@link Void}
     */
    @PostMapping(value = "{chatId}/sharing/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Generate report sharing chat", description = "Generate report sharing chat.")
    @ApiResponse(responseCode = "200", description = "Success generate report sharing chat.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<ResponseEntity<Flux<DefaultDataBuffer>>> generatePdfReport(@CurrentUser UserData userData,
                                                                           @PathVariable("chatId") String chatId) {

        log.info("Solicitud de generación de reporte PDF: sharing pdf");

        return iGeneratePDFSharingChatUseCase.handle(chatId, userData.getUid())
                .map(pdfBytes -> {
                    final var buffer = new DefaultDataBufferFactory().wrap(ByteBuffer.wrap(pdfBytes));

                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION,
                                    "attachment; filename=\"" + FILENAME_CHAT_SHARING_PDF + ".pdf\"")
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(Flux.just(buffer));
                });
    }


    /**
     * Unlink a folder with chat.
     *
     * @param userData {@link UserData}
     * @param idChat   {@link String}
     * @return {@link ChatResponse}
     */
    @PutMapping("/{idChat}/unlink")
    @Operation(summary = "Link a folder with chat", description = "Link a folder with chat.")
    @ApiResponse(responseCode = "200", description = "Success link a folder with chat.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<ChatResponse> unlinkFolderWithChat(@CurrentUser UserData userData,
                                                   @PathVariable("idChat") String idChat) {
        return updateChatUseCase.handle(userData.getUid(), null, idChat);
    }

}
