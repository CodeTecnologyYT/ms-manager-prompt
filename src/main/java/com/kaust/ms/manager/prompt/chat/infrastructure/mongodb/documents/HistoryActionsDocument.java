package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "history_actions")
public class HistoryActionsDocument {

    /* id. */
    @Id
    private String id;
    /* userId. */
    private String userId;
    /* action. */
    private ACTION action;
    /* messageId. */
    private String messageId;
    /* chatId. */
    private String chatId;
    /* folderId. */
    private String folderId;
    /* numberOfTokens. */
    private Integer numberOfTokens;
    /* model. */
    private String model;
    /* quantityCreativity. */
    private Integer quantityCreativity;
    /* createdAt. */
    private LocalDateTime createdAt;

    public enum ACTION {
        TOKEN,
        LOGIN
    }

}
