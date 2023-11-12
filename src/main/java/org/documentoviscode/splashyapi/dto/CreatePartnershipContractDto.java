package org.documentoviscode.splashyapi.dto;


import lombok.*;
import org.documentoviscode.splashyapi.config.DocFormat;
import org.documentoviscode.splashyapi.domain.PartnershipContract;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CreatePartnershipContractDto {

    private DocFormat type;

    private String GDriveLink;

    private LocalDate creationDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private double rate;

    private double donationPercentage;

    public static Function<CreatePartnershipContractDto, PartnershipContract> dtoToEntityMapper()
    {
        return request -> PartnershipContract.builder()
                .type(request.getType())
                .GDriveLink(request.getGDriveLink())
                .creationDate(request.getCreationDate())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .rate(request.getRate())
                .donationPercentage(request.getDonationPercentage())
                .contractExtensionInProgress(false)
                .build();
    }
}
