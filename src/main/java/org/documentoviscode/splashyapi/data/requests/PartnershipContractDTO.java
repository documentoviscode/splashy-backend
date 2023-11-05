package org.documentoviscode.splashyapi.data.requests;

import jakarta.persistence.Column;
import org.documentoviscode.splashyapi.config.DocFormat;

import java.time.LocalDate;

public record PartnershipContractDTO(
        DocFormat type,
        String GDriveLink,
        LocalDate creationDate,
        LocalDate startDate,
        LocalDate endDate,
        double rate,
        double donationPercentage
) {
}