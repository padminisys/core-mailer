package com.padminisys.mailer.coremailer.dal.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padminisys.mailer.coremailer.dal.entities.MailRequest;

public interface MailRequestRepository extends JpaRepository<MailRequest, UUID> {
}
