package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name="Monthly_Reports")
public class MonthlyReport extends Document{

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column

    private int viewers;

    @Column

    private double hoursWatched;

    @Column

    private double donations;
}
