package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
/**
 * Entity class representing monthly reports.
 */
@Entity
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
