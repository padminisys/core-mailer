package com.padminisys.mailer.coremailer.api.mappers;

import com.padminisys.mailer.coremailer.api.model.contact.ContactCreateRequest;
import com.padminisys.mailer.coremailer.dal.entities.Client;
import com.padminisys.mailer.coremailer.dal.entities.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(imports = Client.class, componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContactMapper {
    @Mapping(target = "client", expression = "java(new Client(contactCreateRequest.getClient()))")
    Contact contactCreateRequestToContact(ContactCreateRequest contactCreateRequest);
}
