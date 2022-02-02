package com.padminisys.mailer.coremailer.api.model.mailrequest;

import lombok.Data;

import java.util.UUID;

@Data
public class MailServiceResponse {
    private UUID id;
    private String status;
}
