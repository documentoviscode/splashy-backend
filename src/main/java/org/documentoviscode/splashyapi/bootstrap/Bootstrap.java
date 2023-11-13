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

        System.out.println("Bootstrap has finished");
    }
}
