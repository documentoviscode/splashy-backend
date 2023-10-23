package org.documentoviscode.splashyapi.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.documentoviscode.splashyapi.config.DocFormat;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="Documents")
public class Document {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private DocFormat type;

    @Column
    private String GDriveLink;

    @Column
    private LocalDate creationDate;

    @ManyToMany
    private List<User> users;
}
