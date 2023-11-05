package org.documentoviscode.splashyapi.data.requests;

import org.documentoviscode.splashyapi.config.DocFormat;

import java.time.LocalDate;

public record SubscriptionDTO(
        DocFormat type,
        String GDriveLink,
        LocalDate creationDate,
        LocalDate startDate,
        int period,
        double monthlyRate
) {
}
