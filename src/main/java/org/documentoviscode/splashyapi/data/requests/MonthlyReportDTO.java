package org.documentoviscode.splashyapi.data.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.documentoviscode.splashyapi.config.DocFormat;

import java.time.LocalDate;
import java.time.LocalDate;

import lombok.Data;

@Data
public class MonthlyReportDTO {
    private DocFormat type;
    private String GDriveLink;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Integer viewers;
    private Double hoursWatched;
    private Double donations;
}
