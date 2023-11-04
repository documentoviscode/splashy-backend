package org.documentoviscode.splashyapi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
/**
 * Entity class representing subscriptions.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
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
