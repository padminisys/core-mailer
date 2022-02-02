package com.padminisys.mailer.coremailer.api.model.mailrequest;

import lombok.Data;

import java.util.UUID;

@Data
public class MailServiceRequest {
    private UUID client;
    private String tag;
    private String mailContentPath;
}
