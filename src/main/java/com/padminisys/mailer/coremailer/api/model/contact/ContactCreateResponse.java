package com.padminisys.mailer.coremailer.api.model.contact;

import lombok.Data;

import java.util.UUID;

@Data
public class ContactCreateResponse {
    private UUID id;
    private String status;
}
