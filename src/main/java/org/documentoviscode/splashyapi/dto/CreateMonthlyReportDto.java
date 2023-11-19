package org.documentoviscode.splashyapi.dto;

import lombok.*;
import org.documentoviscode.splashyapi.config.DocFormat;
import org.documentoviscode.splashyapi.domain.MonthlyReport;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CreateMonthlyReportDto {
    private DocFormat type;

    private String GDriveLink;

    private LocalDate creationDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private int viewers;

    private double hoursWatched;

    private double donations;

    private Double revenue;

    public static Function<CreateMonthlyReportDto, MonthlyReport> dtoToEntityMapper()
    {
        return request -> MonthlyReport.builder()
                .type(request.getType())
                .GDriveLink(request.getGDriveLink())
                .creationDate(request.getCreationDate())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .viewers(request.getViewers())
                .hoursWatched(request.getHoursWatched())
                .donations(request.getDonations())
                .revenue(request.getRevenue())
                .build();
    }
}
