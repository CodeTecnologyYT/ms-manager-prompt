package com.kaust.ms.manager.prompt.chat.infrastructure.web.controllers;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.chat.application.*;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.FolderRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.FolderResponse;
import com.kaust.ms.manager.prompt.shared.anotations.current_user.CurrentUser;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
@Tag(name = "Folder", description = "API folder")
public class FolderController {

    /**
     * saveFolderUseCase.
     */
    private final ISaveFolderUseCase saveFolderUseCase;
    /**
     * getFolderAllByUserIdUseCase.
     */
    private final IGetFolderAllByUserIdUseCase getFolderAllByUserIdUseCase;
    /**
     * getChatAllByFolderIdUseCase.
     */
    private final IGetChatAllByFolderIdUseCase getChatAllByFolderIdUseCase;
    /**
     * updateFolderUseCase.
     */
    private final IUpdateFolderUsecase updateFolderUseCase;
    /**
     * updateChatUseCase.
     */
    private final IUpdateChatUseCase updateChatUseCase;

    /**
     * Create a folder.
     *
     * @param userData      {@link UserData}
     * @param folderRequest {@link FolderRequest}
     * @return {@link FolderResponse}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a folder", description = "Create a folder by user.")
    @ApiResponse(responseCode = "201", description = "Success create a folder by user.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<FolderResponse> createFolder(@CurrentUser UserData userData,
                                             @Valid @RequestBody Mono<FolderRequest> folderRequest) {
        return folderRequest.flatMap(request ->
                saveFolderUseCase.handle(userData.getUid(), request));
    }

    /**
     * Get all folders by user id.
     *
     * @param userData {@link UserData}
     * @param page     {@link Integer}
     * @param size     {@link Integer}
     * @return mono of page {@link FolderResponse}
     */
    @GetMapping
    @Operation(summary = "Get all folders by user id", description = "Retrieve all folders by user id.")
    @ApiResponse(responseCode = "200", description = "Success get all folders by user id.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<PageResponse<FolderResponse>> getAllFoldersByUserId(@CurrentUser UserData userData,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        final var pageable = PageRequest.of(page, size);
        return getFolderAllByUserIdUseCase.getAllByUserId(userData.getUid(), pageable);
    }

    /**
     * Get all chat by folder id.
     *
     * @param userData {@link UserData}
     * @param idFolder {@link String}
     * @param page     {@link Integer}
     * @param size     {@link Integer}
     * @return mono of page {@link ChatResponse}
     */
    @GetMapping("/{idFolder}/chats")
    @Operation(summary = "Get all chat by folder id", description = "Retrieve all chat by folder id.")
    @ApiResponse(responseCode = "200", description = "Success get all chat by folder id.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<PageResponse<ChatResponse>> getAllChatsByFolderId(@CurrentUser UserData userData,
                                                                  @PathVariable("idFolder") String idFolder,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        final var pageable = PageRequest.of(page, size);
        return getChatAllByFolderIdUseCase.handle(userData.getUid(), idFolder, pageable);
    }

    /**
     * Update folder.
     *
     * @param userData      {@link UserData}
     * @param idFolder      {@link String}
     * @param folderRequest {@link FolderRequest}
     * @return {@link FolderResponse}
     */
    @PutMapping("/{idFolder}")
    @Operation(summary = "Update folder", description = "Update folder by user.")
    @ApiResponse(responseCode = "200", description = "Success update folder by user.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<FolderResponse> updateFolder(@CurrentUser UserData userData,
                                             @PathVariable("idFolder") String idFolder,
                                             @Valid @RequestBody Mono<FolderRequest> folderRequest) {
        return folderRequest.flatMap(request ->
                updateFolderUseCase.handle(userData.getUid(), idFolder, request));
    }

    /**
     * Link a folder with chat.
     *
     * @param userData {@link UserData}
     * @param idFolder {@link String}
     * @param idChat   {@link String}
     * @return {@link ChatResponse}
     */
    @PutMapping("/{idFolder}/chats/{idChat}")
    @Operation(summary = "Link a folder with chat", description = "Link a folder with chat.")
    @ApiResponse(responseCode = "200", description = "Success link a folder with chat.")
    @ApiResponse(responseCode = "500", description = "Unexpected error.",
            content = @Content(schema = @Schema(hidden = true)))
    public Mono<ChatResponse> linkFolderWithChat(@CurrentUser UserData userData,
                                                 @PathVariable("idFolder") String idFolder,
                                                 @PathVariable("idChat") String idChat) {
        return updateChatUseCase.handle(userData.getUid(), idFolder, idChat);
    }

}
