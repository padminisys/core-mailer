package com.padminisys.mailer.coremailer.dal.repos;

import com.padminisys.mailer.coremailer.dal.entities.Client;
import com.padminisys.mailer.coremailer.dal.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
    List<Contact> findContactsByClientAndTagAndOptOut(Client client, String tag, boolean optOut);
}