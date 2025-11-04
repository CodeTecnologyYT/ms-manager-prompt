package com.kaust.ms.manager.prompt.chat.infrastructure.web.controllers;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.chat.application.*;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.ChatRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.MailRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.ModelRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.SearchPromptRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.MessageResponse;
import com.kaust.ms.manager.prompt.report.application.IGeneratePDFUseCase;
import com.kaust.ms.manager.prompt.shared.anotations.current_user.CurrentUser;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
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
     * @param chatRequest {@link ChatRequest}
     * @return mono {@link String}
     */
    @PostMapping
    public Mono<ChatResponse> createChat(@CurrentUser UserData userData, @RequestBody ChatRequest chatRequest) {
        return iSaveChatUseCase.handle(userData.getUid(), chatRequest);
    }

    /**
     * Get all chats by user id.
     *
     * @param userData {@link UserData}
     * @return mono of page {@link ChatResponse}
     */
    @GetMapping
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
    public Mono<PageResponse<ChatResponse>> searchChatByText(@CurrentUser UserData userData,
                                                             @RequestBody SearchPromptRequest search,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        final var pageable = PageRequest.of(page, size);
        return iSearchChatUseCase.handle(userData.getUid(), search.getTextSearch(), pageable);
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
    public Mono<ChatResponse> updateChatModel(@CurrentUser UserData userData,
                                              @PathVariable("chatId") String chatId,
                                              @RequestBody final ModelRequest modelRequest) {
        return iUpdateChatByModelUseCase.handle(chatId, userData.getUid(), modelRequest);
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
    public Mono<Void> sendEmailSharingChat(@CurrentUser UserData userData,
                                           @PathVariable("chatId") String chatId,
                                           @RequestBody final MailRequest mailRequest) {
        return iGeneratePDFSharingChatUseCase.handle(chatId, userData.getUid()).
                flatMap(bytes -> iSendMailSharingChatUseCase.handle(mailRequest.getTo(), userData.getUid(), chatId, bytes));
    }

    /**
     * Send email sharing chat.
     *
     * @param userData {@link UserData}
     * @param chatId   {@link String}
     * @return mono{@link Void}
     */
    @PostMapping(value = "{chatId}/sharing/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
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

}
