package com.padminisys.mailer.coremailer.service.model;

import com.padminisys.mailer.coremailer.dal.entities.Client;
import com.padminisys.mailer.coremailer.dal.entities.Contact;
import com.padminisys.mailer.coremailer.dal.entities.MailRequest;
import com.padminisys.mailer.coremailer.dal.entities.MailTransaction;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

@Data
@RequiredArgsConstructor
public class Mailer {

    private final Client client;
    private final MailRequest mailRequest;
    private final Contact contact;

    private JavaMailSender javaMailSender;
    private MimeMessageHelper mimeMessageHelper;
    private MimeMessage mimeMessage;
    private MailTransaction mailTransaction;

}
