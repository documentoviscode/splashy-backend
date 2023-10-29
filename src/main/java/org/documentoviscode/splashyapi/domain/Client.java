package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;



/**
 * Entity class representing clients.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@Entity
@Table(name = "Clients")
public class Client extends User {

    /**
     * The credit card associated with the client.
     */
    @OneToOne
    private CreditCard creditCard;

}
