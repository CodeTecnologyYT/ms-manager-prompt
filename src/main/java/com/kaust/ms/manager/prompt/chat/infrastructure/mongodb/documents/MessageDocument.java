package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents;

import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
public class MessageDocument {

    /** id. */
    @Id
    private String id;
    /** chatId. */
    private String chatId;
    /** userId. */
    private String userId;
    /** content. */
    private String content;
    /** entities. */
    private List<BiomedicalResponse.Entity> entities;
    /** role. */
    private Role role;
    /** createdAt. */
    private LocalDateTime createdAt;


}
