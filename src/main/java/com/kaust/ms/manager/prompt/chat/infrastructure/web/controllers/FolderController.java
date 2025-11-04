package com.kaust.ms.manager.prompt.chat.infrastructure.web.controllers;

import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import com.kaust.ms.manager.prompt.chat.application.*;
import com.kaust.ms.manager.prompt.chat.domain.models.requests.FolderRequest;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.ChatResponse;
import com.kaust.ms.manager.prompt.chat.domain.models.responses.FolderResponse;
import com.kaust.ms.manager.prompt.shared.anotations.current_user.CurrentUser;
import com.kaust.ms.manager.prompt.shared.models.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
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
    public Mono<FolderResponse> createFolder(@CurrentUser UserData userData,
                                             @RequestBody final FolderRequest folderRequest) {
        return saveFolderUseCase.handle(userData.getUid(), folderRequest);
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
    public Mono<FolderResponse> updateFolder(@CurrentUser UserData userData,
                                             @PathVariable("idFolder") String idFolder,
                                             @RequestBody final FolderRequest folderRequest) {
        return updateFolderUseCase.handle(userData.getUid(), idFolder, folderRequest);
    }

    /**
     * Link a folder with chat.
     *
     * @param userData {@link UserData}
     * @param idFolder {@link String}
     * @param idChat {@link String}
     * @return {@link ChatResponse}
     */
    @PutMapping("/{idFolder}/chats/{idChat}")
    public Mono<ChatResponse> linkFolderWithChat(@CurrentUser UserData userData,
                                                 @PathVariable("idFolder") String idFolder,
                                                 @PathVariable("idChat") String idChat) {
        return updateChatUseCase.handle(userData.getUid(), idFolder, idChat);
    }

}
