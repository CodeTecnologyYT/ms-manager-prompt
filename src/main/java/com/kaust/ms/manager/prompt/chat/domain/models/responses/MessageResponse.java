package com.kaust.ms.manager.prompt.chat.domain.models.responses;

import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    /** content. */
    private String content;
    /** role. */
    private String role;
    /** createdAt. */
    private String createdAt;

}
