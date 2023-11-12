package org.documentoviscode.splashyapi.data.requests;

import org.documentoviscode.splashyapi.config.DocFormat;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PartnershipContractDTO {
    private DocFormat type;
    private String GDriveLink;
    private LocalDate creationDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double rate;
    private Double donationPercentage;
    private Boolean contractExtensionInProgress;
    private Boolean contractExtensionOfferVisible;
}
