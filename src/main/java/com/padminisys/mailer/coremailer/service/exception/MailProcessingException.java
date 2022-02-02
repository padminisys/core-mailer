package com.padminisys.mailer.coremailer.service.exception;

public class MailProcessingException extends Exception {
    public MailProcessingException() {
    }

    public MailProcessingException(String message) {
        super(message);
    }

    public MailProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailProcessingException(Throwable cause) {
        super(cause);
    }

    public MailProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
