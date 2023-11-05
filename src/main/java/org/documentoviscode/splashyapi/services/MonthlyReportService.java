package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.data.requests.MonthlyReportDTO;
import org.documentoviscode.splashyapi.domain.MonthlyReport;
import org.documentoviscode.splashyapi.repositories.MonthlyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Update an existing monthly report.
     *
     * @param id                The ID of the monthly report to be updated.
     * @param updatedMonthlyReport The updated monthly report data.
     * @return The updated monthly report or null if the monthly report with the specified ID is not found.
     */
    public MonthlyReport updateMonthlyReport(Long id, MonthlyReportDTO updatedMonthlyReport) {
        return findMonthlyReportById(id)
                .map(monthlyReportToUpdate -> {
                    monthlyReportToUpdate.setType(updatedMonthlyReport.type());
                    monthlyReportToUpdate.setGDriveLink(updatedMonthlyReport.GDriveLink());
                    monthlyReportToUpdate.setCreationDate(updatedMonthlyReport.creationDate());
                    monthlyReportToUpdate.setStartDate(updatedMonthlyReport.startDate());
                    monthlyReportToUpdate.setEndDate(updatedMonthlyReport.endDate());
                    monthlyReportToUpdate.setViewers(updatedMonthlyReport.viewers());
                    monthlyReportToUpdate.setHoursWatched(updatedMonthlyReport.hoursWatched());
                    monthlyReportToUpdate.setDonations(updatedMonthlyReport.donations());
                    return monthlyReportRepository.save(monthlyReportToUpdate);
                })
                .orElse(null);
    }

}
