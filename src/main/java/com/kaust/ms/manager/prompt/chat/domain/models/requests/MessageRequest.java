package com.kaust.ms.manager.prompt.chat.domain.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Message Request", description = "Request for messages stream",
        example = """
                    {
                        "content": "Quisiera saber sobre las provincias de lima y su historia",
                        "chatId": "690bb4b37f9b586cc3bf0c1e"
                    }
                """)
public class MessageRequest {

    /** content. */
    @NotEmpty(message = "content can't is empty")
    @Schema(description = "content message",requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;
    /** chatId. */
    @NotEmpty(message = "chat id can't is empty")
    @Schema(description = "chatId message",requiredMode = Schema.RequiredMode.REQUIRED)
    private String chatId;

}
