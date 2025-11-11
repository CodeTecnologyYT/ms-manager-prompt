package com.kaust.ms.manager.prompt.shared.exceptions;

import java.io.Serializable;

public interface Error extends Serializable {

    /**
     * Obtiene el code
     *
     * @return {@link String}
     */
    String getCode();

    /**
     * Obtiene el Message
     *
     * @return {@link String}
     */
    String getMessage();

}
