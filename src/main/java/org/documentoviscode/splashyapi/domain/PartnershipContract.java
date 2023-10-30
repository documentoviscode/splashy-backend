package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Entity class representing partnership agreements.
 */
@Entity
@Table(name = "Partnership_Contracts")
public class PartnershipContract extends Document {
    /**
     * The start date of the partnership agreement.
     */
    @Column
    private LocalDate startDate;

    /**
     * The end date of the partnership agreement.
     */
    @Column
    private LocalDate endDate;

    /**
     * The rate of the partnership agreement.
     */
    @Column
    private double rate;

    /**
     * The donation percentage specified in the partnership agreement.
     */
    @Column
    private double donationPercentage;
}
