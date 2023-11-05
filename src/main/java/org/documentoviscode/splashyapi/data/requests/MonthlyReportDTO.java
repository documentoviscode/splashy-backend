package org.documentoviscode.splashyapi.data.requests;

import org.documentoviscode.splashyapi.config.DocFormat;

import java.time.LocalDate;

public record MonthlyReportDTO(
        DocFormat type,
        String GDriveLink,
        LocalDate creationDate,
        LocalDate startDate,
        LocalDate endDate,
        int viewers,
        double hoursWatched,
        double donations
) {
}
