package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Entity class representing credit cards.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Table(name = "Credit_Cards")
public class CreditCard {
    /**
     * The unique identifier for the credit card.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The credit card number.
     */
    @Column
    private String number;

    /**
     * The Card Verification Code (CVC) of the credit card.
     */
    @Column
    private String cvc;

    /**
     * The expiration date of the credit card.
     */
    @Column
    private LocalDate expirationDate;
}
