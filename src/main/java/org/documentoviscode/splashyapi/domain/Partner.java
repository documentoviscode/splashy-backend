package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;

/**
 * Entity class representing partners.
 */
@Entity
@Table(name = "Partners")
public class Partner extends User {
    /**
     * The account number of the partner.
     */
    @Column
    private String accountNumber;

    /**
     * The phone number of the partner.
     */
    @Column
    private String phoneNumber;

    /**
     * The PESEL (Personal Identification Number) of the partner.
     */
    @Column
    private String PESEL;
}
