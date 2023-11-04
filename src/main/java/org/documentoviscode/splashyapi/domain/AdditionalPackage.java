package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;

/**
 * Entity class representing additional packages.
 */
@Entity
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
