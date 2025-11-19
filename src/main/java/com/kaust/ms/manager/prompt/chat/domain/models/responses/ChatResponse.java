package com.kaust.ms.manager.prompt.chat.domain.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.web.server.authentication.ott.ServerGenerateOneTimeTokenRequestResolver;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    private String id;
    /** title. */
    private String title;
    /** model. */
    private String model;
    /** quantityCreativity. */
    private Integer quantityCreativity;
    /** createdAt. */
    private LocalDateTime createdAt;
    /** updatedAt. */
    private LocalDateTime updatedAt;

}
