package org.documentoviscode.splashyapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.documentoviscode.splashyapi.config.DocFormat;
import org.documentoviscode.splashyapi.domain.MonthlyReport;

import java.time.LocalDate;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private int viewers;

    private double hoursWatched;

    private double donations;

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
                .build();
    }
}
