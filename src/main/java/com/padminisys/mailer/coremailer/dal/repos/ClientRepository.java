package com.padminisys.mailer.coremailer.dal.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padminisys.mailer.coremailer.dal.entities.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
