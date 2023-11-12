package org.documentoviscode.splashyapi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
/**
 * Entity class representing monthly reports.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "Monthly_Reports")
public class MonthlyReport extends Document {
    /**
     * The start date of the monthly report.
     */
    @Column
    private LocalDate startDate;

    /**
     * The end date of the monthly report.
     */
    @Column
    private LocalDate endDate;

    /**
     * The number of viewers for the monthly report.
     */
    @Column
    private int viewers;

    /**
     * The total hours watched for the monthly report.
     */
    @Column
    private double hoursWatched;

    /**
     * The total donations for the monthly report.
     */
    @Column
    private double donations;
}
