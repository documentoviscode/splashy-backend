package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name="Subscriptions")
public class Subscription extends Document{

    @Column
    private LocalDate startDate;

    @Column
    private int period;

    @Column
    private double monthlyRate;

}
