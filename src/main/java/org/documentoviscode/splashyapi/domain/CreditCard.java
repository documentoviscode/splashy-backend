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
 * Entity class representing credit cards.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
}
