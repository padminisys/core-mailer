package com.padminisys.mailer.coremailer.service.domain.mail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.padminisys.mailer.coremailer.dal.entities.Client;
import com.padminisys.mailer.coremailer.dal.entities.Contact;
import com.padminisys.mailer.coremailer.dal.entities.MailTransaction;
import com.padminisys.mailer.coremailer.dal.repos.MailTransactionRepository;
import com.padminisys.mailer.coremailer.service.model.Mailer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSender {

	private final MailTransactionRepository mailTransactionRepository;
	private final HtmlMessageBuilder htmlMessageBuilder;

	public Mailer implementMailer(Mailer mailer) throws MessagingException, IOException {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Client client = mailer.getClient();
		mailSender.setHost(client.getHost());
		mailSender.setHost(client.getHost());
		mailSender.setUsername(client.getUsername());
		mailSender.setPassword(client.getPassword());
		Properties properties = mailSender.getJavaMailProperties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.debug", "false");
		mailer.setJavaMailSender(mailSender);
		return htmlMessageBuilder.mimeMessageBuilder(mailer);
	}

	public void send(Mailer mailer, MailTransaction mailTransaction) throws MessagingException {
		Contact contact = mailTransaction.getContact();
		try {
			MimeMessageHelper mimeMessageHelper = htmlMessageBuilder.mimeMessageHelperBuilder(mailer, mailTransaction);
			mimeMessageHelper.setTo(contact.getEmail());
			mailer.getJavaMailSender().send(mimeMessageHelper.getMimeMessage());
		} catch (Exception exception) {
			log.error("Mail sender encountered Exception while sending mail to {} for client {} ", contact.getEmail(),
					mailer.getClient().getName(), exception);
			log.error(ExceptionUtils.getStackTrace(exception));
			mailTransaction.setStatus("Failed");
			mailTransaction.setReason(ExceptionUtils.getRootCauseMessage(exception));
			mailTransactionRepository.save(mailTransaction);
			return;
		}
		mailTransaction.setStatus("Sent");
		mailTransactionRepository.save(mailTransaction);
	}
}
