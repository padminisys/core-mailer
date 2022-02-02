package com.padminisys.mailer.coremailer.api.model.client;

import lombok.Data;

@Data
public class ClientCreateRequest {
    private String name;
    private String reporting_mailId;
    private String host;
    private int port;
    private String username;
    private String password;
    private String mailId;
    private String replyToMailId;
}
