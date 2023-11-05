package org.documentoviscode.splashyapi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "Clients")
public class Client extends User {

    /**
     * The credit card associated with the client.
     */
    @OneToOne
    private CreditCard creditCard;

}
