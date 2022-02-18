package com.padminisys.mailer.coremailer.service.model;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;

import com.padminisys.mailer.coremailer.dal.entities.Client;
import com.padminisys.mailer.coremailer.dal.entities.MailRequest;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Mailer {
	private final Client client;
	private final MailRequest mailRequest;
	private JavaMailSender javaMailSender;
	private MimeMessage mimeMessage;
}
