package org.documentoviscode.splashyapi.data.requests;


import org.documentoviscode.splashyapi.config.DocFormat;

import java.time.LocalDate;

public record AdditionalPackageDTO(
        DocFormat type,
        String GDriveLink,
        LocalDate creationDate,
        String packageType,
        double price
) {
}
