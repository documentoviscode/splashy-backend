package org.documentoviscode.splashyapi.data.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.documentoviscode.splashyapi.config.DocFormat;

import java.time.LocalDate;
import java.time.LocalDate;

@Data
public class AdditionalPackageDTO {
    private DocFormat type;
    private String GDriveLink;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    private String packageType;
    private Double price;
}
