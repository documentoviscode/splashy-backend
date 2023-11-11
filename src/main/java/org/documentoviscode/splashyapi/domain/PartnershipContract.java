package org.documentoviscode.splashyapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate startDate;

    /**
     * The end date of the partnership agreement.
     */
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
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
