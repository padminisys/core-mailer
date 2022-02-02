package com.padminisys.mailer.coremailer.service.domain.mail;

import com.padminisys.mailer.coremailer.dal.entities.MailTransaction;
import com.padminisys.mailer.coremailer.dal.repos.MailTransactionRepository;
import com.padminisys.mailer.coremailer.service.model.Mailer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailTransactionBuilder {

    private final MailTransactionRepository mailTransactionRepository;

    public MailTransaction build(Mailer mailer) {
        MailTransaction mailTransaction = new MailTransaction();
        mailTransaction.setClient(mailer.getClient());
        mailTransaction.setContact(mailer.getContact());
        mailTransaction.setStatus("MessageBuilding");
        mailTransaction.setMailRequest(mailer.getMailRequest());
        return mailTransactionRepository.save(mailTransaction);
    }
}
