package com.kaust.ms.manager.prompt.chat.infrastructure.mongodb.documents;

import com.kaust.ms.manager.prompt.chat.domain.enums.Role;
import com.kaust.ms.manager.prompt.chat.infrastructure.ia.model.BiomedicalChatResponse;
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
    /** messageUser. */
    private String messageUser;
    /** entities. */
    private List<BiomedicalChatResponse.Entity> entities;
    /** extractedCandidates. */
    private List<String> extractedCandidates;
    /** unmatchedCandidates. */
    private List<String> unmatchedCandidates;
    /** triplets. */
    private List<BiomedicalChatResponse.Triplet> triplets;
    /** chuckPattern. */
    private BiomedicalChatResponse.ChuckPattern chunkPatterns;
    /** chucks. */
    private List<BiomedicalChatResponse.Chunk> chunks;
    /** role. */
    private Role role;
    /** createdAt. */
    private LocalDateTime createdAt;


}
