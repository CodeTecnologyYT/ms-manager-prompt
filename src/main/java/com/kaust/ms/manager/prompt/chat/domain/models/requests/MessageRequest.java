package com.kaust.ms.manager.prompt.chat.domain.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    /** content. */
    private String content;
    /** chatId. */
    private String chatId;

}
