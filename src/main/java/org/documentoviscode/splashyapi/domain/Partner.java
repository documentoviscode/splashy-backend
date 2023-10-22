package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;

@Entity
@Table(name="Partners")
public class Partner extends User{

    @Column
    private String accountNumber;

    @Column
    private String phoneNumber;

    @Column
    private String PESEL;
}
