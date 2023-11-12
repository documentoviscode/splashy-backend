package org.documentoviscode.splashyapi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Entity class representing partnership agreements.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    /**
     * Is contract extension in progress
     */
    @Column
    private Boolean contractExtensionInProgress;
}
