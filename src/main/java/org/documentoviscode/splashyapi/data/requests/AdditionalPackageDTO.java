package org.documentoviscode.splashyapi.data.requests;

import lombok.Data;
import org.documentoviscode.splashyapi.config.DocFormat;

import java.time.LocalDate;

@Data
public class AdditionalPackageDTO {
    private DocFormat type;
    private String GDriveLink;
    private LocalDate creationDate;
    private String packageType;
    private Double price;
}
