package org.documentoviscode.splashyapi.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.documentoviscode.splashyapi.config.DocFormat;
import org.documentoviscode.splashyapi.domain.Subscription;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CreateSubscriptionDto {

    private DocFormat type;

    private String GDriveLink;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    private int period;

    private double monthlyRate;

    public static Function<CreateSubscriptionDto, Subscription> dtoToEntityMapper()
    {
        return request -> Subscription.builder()
                .type(request.getType())
                .GDriveLink(request.getGDriveLink())
                .creationDate(request.getCreationDate())
                .startDate(request.getStartDate())
                .period(request.getPeriod())
                .monthlyRate(request.getMonthlyRate())
                .build();
    }

}
