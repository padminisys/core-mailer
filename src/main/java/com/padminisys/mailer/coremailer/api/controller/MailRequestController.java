package com.padminisys.mailer.coremailer.api.controller;


import com.padminisys.mailer.coremailer.api.exception.exception.WebRequestProcessingException;
import com.padminisys.mailer.coremailer.api.mappers.MailRequestMapper;
import com.padminisys.mailer.coremailer.api.model.mailrequest.MailServiceRequest;
import com.padminisys.mailer.coremailer.api.model.mailrequest.MailServiceResponse;
import com.padminisys.mailer.coremailer.dal.entities.Client;
import com.padminisys.mailer.coremailer.dal.entities.MailRequest;
import com.padminisys.mailer.coremailer.dal.repos.ClientRepository;
import com.padminisys.mailer.coremailer.dal.repos.MailRequestRepository;
import com.padminisys.mailer.coremailer.service.domain.mail.MailSender;
import com.padminisys.mailer.coremailer.service.domain.mail.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("mailer")
public class MailRequestController {

    private final MailSender mailSender;
    private final MailRequestRepository mailRequestRepository;
    private final MailRequestMapper mailRequestMapper;
    private final MailService mailService;
    private final ClientRepository clientRepository;

    @PostMapping("/invoke")
    public @ResponseBody
    MailServiceResponse newInvocation(@RequestBody MailServiceRequest mailServiceRequest) throws WebRequestProcessingException {
        Optional<Client> client;
        MailRequest mailRequest;
        try {
            mailRequest = mailRequestRepository.save(mailRequestMapper.mailServiceRequestToMailRequest(mailServiceRequest));
            client = clientRepository.findById(mailRequest.getClient().getId());
            client.ifPresent(mailRequest::setClient);
        } catch (Exception exception) {
            throw new WebRequestProcessingException(exception);
        }
        mailService.process(mailRequest);
        MailServiceResponse mailServiceResponse = new MailServiceResponse();
        mailServiceResponse.setId(mailRequest.getId());
        mailServiceResponse.setStatus(mailRequest.getStatus());
        return mailServiceResponse;
    }
}
