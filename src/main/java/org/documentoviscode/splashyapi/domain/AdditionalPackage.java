package org.documentoviscode.splashyapi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing additional packages.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "Additional_Packages")
public class AdditionalPackage extends Document {

    /**
     * The type of the additional package.
     */
    @Column
    private String packageType;

    /**
     * The price of the additional package.
     */
    @Column
    private double price;

}
