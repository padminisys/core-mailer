package com.padminisys.mailer.coremailer.api.mappers;

import com.padminisys.mailer.coremailer.api.model.client.ClientCreateRequest;
import com.padminisys.mailer.coremailer.dal.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {
    Client clientCreateRequestToClient(ClientCreateRequest clientCreateRequest);
}
