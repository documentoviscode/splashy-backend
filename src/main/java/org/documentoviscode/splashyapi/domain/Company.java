package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder

@Entity
@Scope("singleton")
@Table(name="Companies")
public class Company {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String NIP;

    @Column
    private String Address;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @OneToMany
    private List<User> workers;
    @OneToMany
    private List<Partner> partners;
    @OneToMany
    private List<Client> clients;

}
