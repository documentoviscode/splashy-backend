package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder

@Entity
@Table(name="Credit_Cards")
public class CreditCard {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String cvc;

    private LocalDate expirationDate;
}
