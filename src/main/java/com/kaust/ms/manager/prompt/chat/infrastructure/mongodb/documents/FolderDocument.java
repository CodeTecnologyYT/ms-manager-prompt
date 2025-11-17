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
@Document(collection = "folders")
public class FolderDocument {

    /** id. */
    @Id
    private String id;
    /** name. */
    private String name;
    /** description. */
    private String description;
    /** state. */
    private State state;
    /** userId. */
    private String userId;
    /** createdAt. */
    private LocalDateTime createdAt;
    /** updatedAt. */
    private LocalDateTime updatedAt;

    /**
     * State of the folder.
     *
     */
    public enum State {
        ACTIVE,
        DELETE
    }

}
