package org.documentoviscode.splashyapi.bootstrap;

import org.documentoviscode.splashyapi.config.DocFormat;
import org.documentoviscode.splashyapi.controllers.MonthlyReportController;
import org.documentoviscode.splashyapi.dto.CreateMonthlyReportDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class Bootstrap implements CommandLineRunner {
    private final ApplicationContext applicationContext;

    public Bootstrap(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running bootstrap...");

        MonthlyReportController monthlyReportController = applicationContext.getBean(MonthlyReportController.class);
        monthlyReportController.createMonthlyReport(
                CreateMonthlyReportDto.builder()
                        .type(DocFormat.PDF)
                        .creationDate(LocalDate.of(2023, 1, 1))
                        .startDate(LocalDate.of(2023, 9, 1))
                        .endDate(LocalDate.of(2023, 9, 30))
                        .viewers(1111)
                        .hoursWatched(45649.5)
                        .donations(123123)
                        .build(),
                3L
        );

        System.out.println("Bootstrap has finished");
    }
}
