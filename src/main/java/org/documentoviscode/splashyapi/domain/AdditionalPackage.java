package org.documentoviscode.splashyapi.domain;

import jakarta.persistence.*;

@Entity
@Table(name="Additional_Packages")
public class AdditionalPackage extends Document{

    @Column
    private String type;

    @Column
    private double price;

}
