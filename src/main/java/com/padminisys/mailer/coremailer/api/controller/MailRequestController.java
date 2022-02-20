package com.padminisys.mailer.coremailer.api.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.padminisys.mailer.coremailer.api.exception.exception.WebRequestProcessingException;
import com.padminisys.mailer.coremailer.api.mappers.MailRequestMapper;
import com.padminisys.mailer.coremailer.api.model.mailrequest.MailServiceRequest;
import com.padminisys.mailer.coremailer.api.model.mailrequest.MailServiceResponse;
import com.padminisys.mailer.coremailer.dal.entities.Client;
import com.padminisys.mailer.coremailer.dal.entities.MailRequest;
import com.padminisys.mailer.coremailer.dal.repos.ClientRepository;
import com.padminisys.mailer.coremailer.dal.repos.MailRequestRepository;
import com.padminisys.mailer.coremailer.service.domain.mail.MailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("mailer")
public class MailRequestController {

	private final MailRequestRepository mailRequestRepository;
	private final MailRequestMapper mailRequestMapper;
	private final MailService mailService;
	private final ClientRepository clientRepository;

	@PostMapping("/invoke")
	public @ResponseBody MailServiceResponse newInvocation(@RequestBody MailServiceRequest mailServiceRequest)
			throws WebRequestProcessingException {
		Optional<Client> client;
		MailRequest mailRequest;
		try {
			mailRequest = mailRequestRepository
					.save(mailRequestMapper.mailServiceRequestToMailRequest(mailServiceRequest));
			client = clientRepository.findById(mailRequest.getClient().getId());
			client.ifPresent(mailRequest::setClient);
			mailService.process(mailRequest);
		} catch (Exception exception) {
			throw new WebRequestProcessingException(exception);
		}
		MailServiceResponse mailServiceResponse = new MailServiceResponse();
		mailServiceResponse.setId(mailRequest.getId());
		mailServiceResponse.setStatus(mailRequest.getStatus());
		return mailServiceResponse;
	}
}
