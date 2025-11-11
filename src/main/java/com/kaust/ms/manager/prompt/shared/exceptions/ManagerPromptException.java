package com.kaust.ms.manager.prompt.shared.exceptions;

import lombok.Getter;

@Getter
public class ManagerPromptException extends RuntimeException {

    private static final long serialVersionUID = 1905122041950251207L;
    private final int status;
    private final String code;
    private final Error errorEnum;
    private final String source;

    public ManagerPromptException(final Error enumError) {
        this(enumError, (Throwable) null);
    }

    public ManagerPromptException(final Error enumError, final Throwable cause) {
        this(enumError, 500, cause);
    }

    public ManagerPromptException(final Error enumError, final int httpStatus) {
        this(enumError, httpStatus, (Throwable) null);
    }

    public ManagerPromptException(final Error enumError, final int httpStatus, final Throwable cause) {
        super(enumError.getMessage(), cause);
        this.source = ManagerPromptException.class.getName();
        this.errorEnum = enumError;
        this.status = httpStatus;
        this.code = enumError.getCode();
    }

    private ManagerPromptException(final String code, final String message, final int httpStatus, final Throwable cause) {
        super(message, cause);
        this.source = ManagerPromptException.class.getName();
        this.errorEnum = ManagerPromptError.DEFAULT;
        this.code = code;
        this.status = httpStatus;
    }

}
