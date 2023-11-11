package org.documentoviscode.splashyapi.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.documentoviscode.splashyapi.config.DocFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;



/**
 * Entity class representing documents.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "Documents")
public class Document {
    /**
     * The unique identifier for the document.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The format of the document, such as PDF, CSV, DOCX, JSON, or XML.
     */
    @Column
    private DocFormat type;

    /**
     * The Google Drive link to the document.
     */
    @Column
    private String GDriveLink;

    /**
     * The creation date of the document.
     */
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    /**
     * The list of users associated with the document.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private User user;

}
