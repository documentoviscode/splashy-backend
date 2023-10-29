package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
/**
 * Entity class representing subscriptions.
 */
@Entity
@Table(name = "Subscriptions")
public class Subscription extends Document {
    /**
     * The start date of the subscription.
     */
    @Column
    private LocalDate startDate;

    /**
     * The period of the subscription in months.
     */
    @Column
    private int period;

    /**
     * The monthly rate of the subscription.
     */
    @Column
    private double monthlyRate;
}
