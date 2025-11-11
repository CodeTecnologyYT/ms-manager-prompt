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
@Schema(name = "Chat Request", description = "Request for chat register",
        example = """
                        {
                            "content": "what the last paper in this year?"
                        }
                """)
public class ChatRequest {

    /** content. */
    @NotEmpty(message = "Title can is not empty")
    @Schema(description = "title chat",requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

}
