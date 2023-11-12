package org.documentoviscode.splashyapi.bootstrap;

import lombok.RequiredArgsConstructor;
import org.documentoviscode.splashyapi.config.DocFormat;
import org.documentoviscode.splashyapi.controllers.MonthlyReportController;
import org.documentoviscode.splashyapi.domain.MonthlyReport;
import org.documentoviscode.splashyapi.dto.CreateMonthlyReportDto;
import org.documentoviscode.splashyapi.repositories.MonthlyReportRepository;
import org.documentoviscode.splashyapi.services.MonthlyReportService;
import org.documentoviscode.splashyapi.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.time.LocalDate;


@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final UserService userService;
    private final MonthlyReportService monthlyReportService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running bootstrap...");

        monthlyReportService.create(
                MonthlyReport.builder()
                        .type(DocFormat.PDF)
                        .creationDate(LocalDate.of(2023, 1, 1))
                        .startDate(LocalDate.of(2023, 9, 1))
                        .endDate(LocalDate.of(2023, 9, 30))
                        .viewers(1111)
                        .hoursWatched(45649.5)
                        .donations(123123)
                        .user(userService.findUserById(3L).get())
                        .build()
        );

        System.out.println("Bootstrap has finished");
    }
}
