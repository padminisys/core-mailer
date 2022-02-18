package com.padminisys.mailer.coremailer.service.domain.mail;

import org.springframework.stereotype.Service;

import com.padminisys.mailer.coremailer.dal.entities.Contact;
import com.padminisys.mailer.coremailer.dal.entities.MailTransaction;
import com.padminisys.mailer.coremailer.dal.repos.MailTransactionRepository;
import com.padminisys.mailer.coremailer.service.model.Mailer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailTransactionBuilder {

	private final MailTransactionRepository mailTransactionRepository;

	public MailTransaction build(Mailer mailer, Contact contact) {
		MailTransaction mailTransaction = new MailTransaction();
		mailTransaction.setClient(mailer.getClient());
		mailTransaction.setContact(contact);
		mailTransaction.setStatus("MessageBuilding");
		mailTransaction.setMailRequest(mailer.getMailRequest());
		return mailTransactionRepository.save(mailTransaction);
	}
}
