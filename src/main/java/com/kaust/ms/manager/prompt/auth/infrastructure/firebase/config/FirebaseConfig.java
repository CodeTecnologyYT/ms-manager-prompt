package com.kaust.ms.manager.prompt.auth.infrastructure.firebase.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptError;
import com.kaust.ms.manager.prompt.shared.exceptions.ManagerPromptException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    /* serviceAccountJson. */
    @Value("${firebase.service-account-json}")
    private String serviceAccountJson;

    /**
     * Initialize firebase.
     *
     * @throws IOException {@link IOException}
     */
    @PostConstruct
    public void initialize() throws IOException {
        try {
            final var credential = new ByteArrayInputStream(serviceAccountJson.getBytes(StandardCharsets.UTF_8));

            final var options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(credential))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new ManagerPromptException(ManagerPromptError.ERROR_FIREBASE_CONFIG, HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        }
    }
}
