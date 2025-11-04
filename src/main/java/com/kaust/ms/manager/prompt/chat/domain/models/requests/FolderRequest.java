package com.kaust.ms.manager.prompt.chat.domain.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FolderRequest {

    /** name. */
    private String name;
    /** description. */
    private String description;

}
