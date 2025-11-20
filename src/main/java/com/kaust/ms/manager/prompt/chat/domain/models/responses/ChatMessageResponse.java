package com.kaust.ms.manager.prompt.chat.domain.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ChatMessageResponse {

    /** content. */
    private String content;
    /** idMessage. */
    private String idMessage;

}
