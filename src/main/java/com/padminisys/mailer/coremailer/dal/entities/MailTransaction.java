package com.padminisys.mailer.coremailer.dal.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class MailTransaction {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.TABLE)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mailRequestId", referencedColumnName = "id", nullable = false)
    private MailRequest mailRequest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contactId", referencedColumnName = "id", nullable = false)
    private Contact contact;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId", referencedColumnName = "id", nullable = false)
    private Client client;

    private String status;

    private String reason;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;
}
