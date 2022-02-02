package com.padminisys.mailer.coremailer.api.mappers;

import com.padminisys.mailer.coremailer.api.model.mailrequest.MailServiceRequest;
import com.padminisys.mailer.coremailer.dal.entities.Client;
import com.padminisys.mailer.coremailer.dal.entities.MailRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(imports = Client.class, componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MailRequestMapper {
    @Mapping(target = "client", expression = "java(new Client(mailServiceRequest.getClient()))")
    @Mapping(target = "status", constant = "processing")
    MailRequest mailServiceRequestToMailRequest(MailServiceRequest mailServiceRequest);
}
