package org.documentoviscode.splashyapi.dto;


import lombok.*;
import org.documentoviscode.splashyapi.config.DocFormat;
import org.documentoviscode.splashyapi.domain.AdditionalPackage;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CreateAdditionalPackageDto {

    private DocFormat type;

    private String GDriveLink;

    private LocalDate creationDate;

    private String packageType;

    private double price;

    public static Function<CreateAdditionalPackageDto, AdditionalPackage> dtoToEntityMapper()
    {
        return request -> AdditionalPackage.builder()
                .type(request.getType())
                .GDriveLink(request.getGDriveLink())
                .creationDate(request.getCreationDate())
                .packageType(request.getPackageType())
                .price(request.getPrice())
                .build();
    }
}
