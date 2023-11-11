package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Entity class representing companies.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Scope("singleton")
@Table(name = "Companies")
public class Company {
    /**
     * The unique identifier for the company.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The NIP (Tax Identification Number) of the company.
     */
    @Column
    private String NIP;

    /**
     * The address of the company.
     */
    @Column
    private String Address;

    /**
     * The email address of the company.
     */
    @Column
    private String email;

    /**
     * The phone number of the company.
     */
    @Column
    private String phoneNumber;

    /**
     * The list of workers associated with the company.
     */
    @OneToMany
    private List<User> workers;

    /**
     * The list of partners associated with the company.
     */
    @OneToMany
    private List<Partner> partners;

    /**
     * The list of clients associated with the company.
     */
    @OneToMany
    private List<Client> clients;

}
