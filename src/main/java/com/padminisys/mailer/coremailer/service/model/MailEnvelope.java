package com.padminisys.mailer.coremailer.service.model;

import org.springframework.mail.javamail.MimeMessageHelper;

import com.padminisys.mailer.coremailer.dal.entities.MailTransaction;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MailEnvelope {
	private final MailTransaction mailTransaction;
	private MimeMessageHelper mimeMessageHelper;
}
