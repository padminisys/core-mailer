package com.padminisys.mailer.coremailer.service.domain.mail;

import com.padminisys.mailer.coremailer.dal.entities.Contact;
import com.padminisys.mailer.coremailer.dal.entities.MailRequest;
import com.padminisys.mailer.coremailer.dal.entities.MailTransaction;
import com.padminisys.mailer.coremailer.dal.repos.ContactRepository;
import com.padminisys.mailer.coremailer.dal.repos.MailRequestRepository;
import com.padminisys.mailer.coremailer.service.model.MailEnvelope;
import com.padminisys.mailer.coremailer.service.model.Mailer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final ContactRepository contactRepository;
    private final MailRequestRepository mailRequestRepository;
    private final MailSender mailSender;
    private final MailTransactionBuilder mailTransactionBuilder;

    @Async
    public CompletableFuture<MailRequest> process(MailRequest mailRequest) {
        List<Contact> contacts = contactRepository.findContactsByClientAndTagAndOptOut(mailRequest.getClient(), mailRequest.getTag(), false);
            Mailer mailer = new Mailer(mailRequest.getClient(), mailRequest);
            try {
                mailer = mailSender.implementMailer(mailer);
            } catch (MessagingException messagingException) {
                log.error("mail sender encountered Messaging Exception while creating mailer for tag {} for client {} ", mailRequest.getTag(), mailRequest.getClient().getName(), messagingException);
                log.error(ExceptionUtils.getStackTrace(messagingException));
            } catch (IOException ioException) {
                log.error("mail sender encountered IO Exception while creating mailer for tag {} for client {} ", mailRequest.getTag(), mailRequest.getClient().getName(), ioException);
                log.error(ExceptionUtils.getStackTrace(ioException));
            } catch (Exception exception) {
                log.error("Unexpected exception encountered for tag {} for client {} ", mailRequest.getTag(), mailRequest.getClient().getName(), exception);
                log.error(ExceptionUtils.getStackTrace(exception));
            }
            
            for (Contact contact : contacts) {
            try {
                mailSender.send(mailer,new MailEnvelope(mailTransactionBuilder.build(mailer,contact)));
            } catch (MessagingException messagingException) {
                log.error("mail sender encountered exception while sending email to {} for client {} ", contact.getEmail(), mailer.getClient().getName(), messagingException);
            }
        }
        return CompletableFuture.completedFuture(mailRequest).whenComplete((request, throwable) -> {
            if (throwable == null) {
                request.setStatus("completed");
            } else {
                request.setStatus(ExceptionUtils.getRootCauseMessage(throwable));
            }
            mailRequestRepository.save(request);
        });
    }
}