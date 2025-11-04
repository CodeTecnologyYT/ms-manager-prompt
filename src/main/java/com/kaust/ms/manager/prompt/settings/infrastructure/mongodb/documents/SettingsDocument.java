package com.kaust.ms.manager.prompt.settings.infrastructure.mongodb.documents;

import com.kaust.ms.manager.prompt.settings.domain.enums.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "settings")
public class SettingsDocument {

    /** id. */
    private String id;
    /** userId. */
    private String userId;
    /** model. */
    private Model model;
    /** quantityCreativity. */
    private Integer quantityCreativity;
    /** createdAt. */
    private LocalDateTime createdAt;
    /** updatedAt. */
    private LocalDateTime updatedAt;

}
