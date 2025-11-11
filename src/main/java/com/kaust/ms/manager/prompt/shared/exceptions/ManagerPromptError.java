package com.kaust.ms.manager.prompt.shared.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ManagerPromptError implements Error {

    /**
     * DEFAULT.
     */
    DEFAULT("99", "Error Generico"),
    /**
     * ERROR_AUTHENTICATION_EXPIRE.
     */
    ERROR_AUTHENTICATION_EXPIRE("410", "El token ha expirado"),
    /**
     * ERROR_AUTHENTICATION_NOT_TOKEN.
     */
    ERROR_AUTHENTICATION_NOT_TOKEN("411", "Token de autenticación no proporcionado"),
    /**
     * ERROR_AUTHENTICATION_NOT_TOKEN.
     */
    ERROR_AUTHENTICATION_INVALID("412", "Token de autenticación es invalido"),
    /**
     * ERROR_SETTINGS_NOT_FOUND.
     */
    ERROR_SETTINGS_NOT_FOUND("420", "No se encontro la configuracion"),
    /**
     * ERROR_SETTINGS_EXIST.
     */
    ERROR_SETTINGS_EXIST("421", "La configuracion ya existe"),
    /**
     * ERROR_CHAT_NOT_FOUND.
     */
    ERROR_CHAT_NOT_FOUND("430", "El chat no fue encontrado"),
    /**
     * ERROR_USER_NOT_FOUND.
     */
    ERROR_USER_NOT_FOUND("440", "Usuario no encontrado"),
    /**
     * ERROR_REPORT_CORRUPT.
     */
    ERROR_REPORT_CORRUPT("450", "Reporte genero error"),
    /**
     * ERROR_FIREBASE_CONFIG.
     */
    ERROR_FIREBASE_CONFIG("460", "Error al configurar firebase"),
    /**
     * ERROR_MAIL_SENDING.
     */
    ERROR_MAIL_SENDING("470", "Error al enviar un correo"),
    /**
     * ERROR_FOLDER_NOT_FOUND.
     */
    ERROR_FOLDER_NOT_FOUND("480", "El folder no fue encontrado");

    /**
     * code.
     */
    private final String code;
    /**
     * message.
     */
    private final String message;

    // -------------------------------------------------------------------
    // -- Métodos Sobrescritos -------------------------------------------
    // -------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    // -------------------------------------------------------------------
    // -- Métodos Públicos -----------------------------------------------
    // -------------------------------------------------------------------

    /**
     * Obtiene enum de Error segun parametro.
     *
     * @param code {@link String}
     * @return {@link ManagerPromptError}
     */
    public static ManagerPromptError getErrorByCode(final String code) {
        for (final ManagerPromptError error : ManagerPromptError.values()) {
            if (error.getCode().equalsIgnoreCase(code)) {
                return error;
            }
        }
        return DEFAULT;
    }

}
