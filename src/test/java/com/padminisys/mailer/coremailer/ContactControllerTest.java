package com.padminisys.mailer.coremailer;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.boot.test.context.SpringBootTest;

import com.padminisys.mailer.coremailer.api.controller.ContactController;
import com.padminisys.mailer.coremailer.api.exception.exception.WebRequestProcessingException;
import com.padminisys.mailer.coremailer.api.model.contact.ContactCreateRequest;
import com.padminisys.mailer.coremailer.api.model.contact.ContactCreateResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ContactControllerTest {

	private final ContactController contactController;

	@Disabled
	@ParameterizedTest
	@CsvFileSource(resources = "/5kMailAddressToShootMail.csv")
	public void newContactCreationFromCsv(String email) throws WebRequestProcessingException {
		ContactCreateRequest contactCreateRequest = new ContactCreateRequest();
		contactCreateRequest.setEmail(email);
		contactCreateRequest.setClient(UUID.fromString("d8e2cc3f-dd3d-4e94-aa61-4108e297cea0"));
		contactCreateRequest.setOptOut(false);
		contactCreateRequest.setTag("promotional");
		ContactCreateResponse contactCreateResponse = contactController.newContact(contactCreateRequest);
		log.info(contactCreateResponse.toString());
	}
}