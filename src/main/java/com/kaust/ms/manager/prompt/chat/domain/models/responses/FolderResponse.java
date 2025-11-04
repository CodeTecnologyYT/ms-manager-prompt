package com.kaust.ms.manager.prompt.chat.domain.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FolderResponse {

    /** id. */
    private String id;
    /** name. */
    private String name;
    /** description. */
    private String description;

}
