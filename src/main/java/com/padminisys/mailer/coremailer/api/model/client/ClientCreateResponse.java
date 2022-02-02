package com.padminisys.mailer.coremailer.api.model.client;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientCreateResponse {
    private UUID id;
    private String status;
}
