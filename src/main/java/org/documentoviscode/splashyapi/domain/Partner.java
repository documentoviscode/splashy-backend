package org.documentoviscode.splashyapi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing partners.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "Partners")
public class Partner extends User {
    /**
     * The account number of the partner.
     */
    @Column
    private String accountNumber;

    /**
     * The phone number of the partner.
     */
    @Column
    private String phoneNumber;

    /**
     * The PESEL (Personal Identification Number) of the partner.
     */
    @Column
    private String PESEL;

    /**
     * Partner nickname that they are known by on the platform
     */
    @Column
    private String nickname;
}
