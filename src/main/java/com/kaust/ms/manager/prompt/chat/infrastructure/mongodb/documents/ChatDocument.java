package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents;

import com.kaust.ms.manager.prompt.settings.domain.enums.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chats")
public class ChatDocument {

    /** id. */
    @Id
    private String id;
    /** title. */
    private String title;
    /** userId. */
    private String userId;
    /** folderId. */
    private String folderId;
    /** model. */
    private Model model;
    /** quantityCreativity. */
    private Double quantityCreativity;
    /** state. */
    private State state;
    /** createdAt. */
    private LocalDateTime createdAt;
    /** updatedAt. */
    private LocalDateTime updatedAt;

    /**
     * State of the chat.
     */
    public enum State {
        ACTIVE,
        ARCHIVE,
        DELETE;
    }

}
