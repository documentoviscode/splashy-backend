package org.documentoviscode.splashyapi.data.requests;

import org.documentoviscode.splashyapi.config.DocFormat;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SubscriptionDTO {
    private DocFormat type;
    private String GDriveLink;
    private LocalDate creationDate;
    private LocalDate startDate;
    private Integer period;
    private Double monthlyRate;
}

