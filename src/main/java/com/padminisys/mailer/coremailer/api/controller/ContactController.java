package com.padminisys.mailer.coremailer.api.controller;

import com.padminisys.mailer.coremailer.api.exception.exception.WebRequestProcessingException;
import com.padminisys.mailer.coremailer.api.mappers.ContactMapper;
import com.padminisys.mailer.coremailer.api.model.contact.ContactCreateRequest;
import com.padminisys.mailer.coremailer.api.model.contact.ContactCreateResponse;
import com.padminisys.mailer.coremailer.dal.entities.Contact;
import com.padminisys.mailer.coremailer.dal.entities.MailTransaction;
import com.padminisys.mailer.coremailer.dal.repos.ContactRepository;
import com.padminisys.mailer.coremailer.dal.repos.MailTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("contact")
public class ContactController {

    private final ContactRepository contactRepository;
    private final MailTransactionRepository mailTransactionRepository;
    private final ContactMapper contactMapper;

    @PostMapping("/create")
    public @ResponseBody
    ContactCreateResponse newContact(@RequestBody ContactCreateRequest contactCreateRequest) throws WebRequestProcessingException {
        try {
            Contact contact = contactRepository.save(contactMapper.contactCreateRequestToContact(contactCreateRequest));
            ContactCreateResponse contactCreateResponse = new ContactCreateResponse();
            contactCreateResponse.setId(contact.getId());
            contactCreateResponse.setStatus("CREATED");
            return contactCreateResponse;
        } catch (Exception exception) {
            throw new WebRequestProcessingException(exception);
        }
    }

    @GetMapping("/unsubscribe")
    public String unsubscribe(@RequestParam UUID mailTransactionId) {
        Optional<MailTransaction> mailTransaction = mailTransactionRepository.findById(mailTransactionId);
        if (mailTransaction.isPresent()) {
            Contact contact = mailTransaction.get().getContact();
            contact.setOptOut(true);
            contactRepository.save(contact);
            return "<h2>Unsubscribing request submitted. We will miss you.</h2>";
        }
        return "<h2>Invalid Mail Transaction. This incident will be reported.</h2>";
    }
}
