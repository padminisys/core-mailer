package com.padminisys.mailer.coremailer.api.exception.exception;

public class WebRequestProcessingException extends Exception {
    public WebRequestProcessingException() {
    }

    public WebRequestProcessingException(String message) {
        super(message);
    }

    public WebRequestProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebRequestProcessingException(Throwable cause) {
        super(cause);
    }

    public WebRequestProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}