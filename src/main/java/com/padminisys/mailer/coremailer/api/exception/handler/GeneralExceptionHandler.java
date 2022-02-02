package com.padminisys.mailer.coremailer.api.exception.handler;

import com.padminisys.mailer.coremailer.api.exception.exception.WebRequestProcessingException;
import com.padminisys.mailer.coremailer.api.model.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(WebRequestProcessingException.class)
    public final ErrorResponse handleErrorsInController(WebRequestProcessingException webRequestProcessingException, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(webRequestProcessingException.getMessage());
        Optional<Throwable> cause = Optional.ofNullable(webRequestProcessingException.getCause());
        cause.ifPresent(throwable -> errorResponse.setNotes(throwable.getMessage()));
        return errorResponse;
    }
}
