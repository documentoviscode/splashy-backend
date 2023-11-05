package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.domain.MonthlyReport;
import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.repositories.MonthlyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing monthly report-related operations.
 */
@Service
public class MonthlyReportService {
    private final MonthlyReportRepository monthlyReportRepository;

    /**
     * Constructor for the MonthlyReportService class.
     *
     * @param monthlyReportRepository The repository for managing monthly report entities.
     */
    @Autowired
    public MonthlyReportService(MonthlyReportRepository monthlyReportRepository) {
        this.monthlyReportRepository = monthlyReportRepository;
    }

    /**
     * Retrieve a monthly report by a specified ID.
     *
     * @param id The ID of the monthly report.
     * @return Optional containing the monthly report or an empty optional if the report is not found.
     */
    public Optional<MonthlyReport> findMonthlyReportById(Long id) {
        return monthlyReportRepository.findById(id);
    }

    /**
     * Retrieve a list of all monthly reports.
     *
     * @return List of all monthly report entities.
     */
    public List<MonthlyReport> findAll() {
        return monthlyReportRepository.findAll();
    }

    @Transactional
    public MonthlyReport create(MonthlyReport monthlyReport) {

        return monthlyReportRepository.save(monthlyReport);

    }
}
