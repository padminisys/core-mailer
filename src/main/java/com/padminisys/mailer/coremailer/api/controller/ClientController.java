package com.padminisys.mailer.coremailer.api.controller;


import com.padminisys.mailer.coremailer.api.exception.exception.WebRequestProcessingException;
import com.padminisys.mailer.coremailer.api.mappers.ClientMapper;
import com.padminisys.mailer.coremailer.api.model.client.ClientCreateRequest;
import com.padminisys.mailer.coremailer.api.model.client.ClientCreateResponse;
import com.padminisys.mailer.coremailer.dal.entities.Client;
import com.padminisys.mailer.coremailer.dal.repos.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("client")
public class ClientController {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @PostMapping("/create")
    public @ResponseBody
    ClientCreateResponse newClient(@RequestBody ClientCreateRequest clientCreateRequest) throws WebRequestProcessingException {
        try {
            Client client = clientRepository.save(clientMapper.clientCreateRequestToClient(clientCreateRequest));
            ClientCreateResponse clientCreateResponse = new ClientCreateResponse();
            clientCreateResponse.setId(client.getId());
            clientCreateResponse.setStatus("CREATED");
            return clientCreateResponse;
        } catch (Exception exception) {
            throw new WebRequestProcessingException(exception);
        }
    }
}