package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


/**
 * Entity class representing views.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Table(name = "Views")
public class View {
    /**
     * The unique identifier for the view.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The start date of the view.
     */
    @Column
    private LocalDate startDate;

    /**
     * The total watching time for the view.
     */
    @Column
    private double watchingTime;

    /**
     * The client who viewed the content.
     */
    @ManyToOne
    private Client viewer;

    /**
     * The partner whose content was watched.
     */
    @ManyToOne
    private Partner watched;
}
