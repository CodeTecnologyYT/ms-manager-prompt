package com.kaust.ms.manager.prompt.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

    /** host. */
    @Value("${spring.mail.host}")
    private String host;
    /** port. */
    @Value("${spring.mail.port}")
    private int port;
    /** username. */
    @Value("${spring.mail.username}")
    private String username;
    /** password. */
    @Value("${spring.mail.password}")
    private String password;

    /**
     * Create bean for JavaMailSender.
     *
     * @return {@link JavaMailSender}
     */
    @Bean
    public JavaMailSender javaMailSender() {
        final var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        final var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }

}
