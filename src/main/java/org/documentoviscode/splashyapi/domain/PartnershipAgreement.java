package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="Partnership_Agreements")
public class PartnershipAgreement extends Document{

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column

    private double rate;

    @Column
    private double donationPercentage;
}
