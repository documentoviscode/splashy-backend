package org.documentoviscode.splashyapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonFormat(pattern = "yyyy-MM-dd")
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
