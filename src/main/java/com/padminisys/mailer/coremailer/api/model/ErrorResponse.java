package com.padminisys.mailer.coremailer.api.model;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String notes;
}
