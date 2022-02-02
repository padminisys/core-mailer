package com.padminisys.mailer.coremailer.api.model.contact;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ContactCreateRequest {
    private UUID client;
    private String tag;
    private String name;
    private String address;
    private String city;
    private Date dob;
    private String gender;
    private String email;
    private String mobile;
    private boolean optOut;
}
