package com.kaust.ms.manager.prompt.shared.exceptions;

public class ManagerPromptException extends RuntimeException {

    /**
     * Exception for Manager Prompt.
     *
     * @param message {@link String}
     */
    public ManagerPromptException(String message) {
        super(message);
    }

    /**
     * Exception for Manager Prompt.
     *
     * @param message {@link String}
     * @param cause {@link Throwable}
     */
    public ManagerPromptException(String message, Throwable cause) {
        super(message, cause);
    }
}
