package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.documentoviscode.splashyapi.config.UserRole;

import java.util.List;


/**
 * Entity class representing users.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "Users")
public class User {
    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The first name of the user.
     */
    @Column
    private String name;

    /**
     * The last name of the user.
     */
    @Column
    private String surname;

    /**
     * The email address of the user.
     */
    @Column
    private String email;

    /**
     * The role of the user, such as CLIENT, PARTNER, or ADMIN.
     */
    @Column
    private UserRole role;

    /**
     * The avatar URL of the user.
     */
    @Column
    private String avatar;

    /**
     * The list of documents associated with the user.
     */
    @ManyToMany
    private List<Document> documents;
}