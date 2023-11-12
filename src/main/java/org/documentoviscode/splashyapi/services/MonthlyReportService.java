package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.data.requests.MonthlyReportDTO;
import org.documentoviscode.splashyapi.domain.Document;
import org.documentoviscode.splashyapi.domain.MonthlyReport;
import org.documentoviscode.splashyapi.domain.Partner;
import org.documentoviscode.splashyapi.domain.PartnershipContract;
import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.repositories.MonthlyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        Optional<MonthlyReport> monthlyReport = monthlyReportRepository.findById(id);
        monthlyReport.ifPresent(mr -> {
            Partner partner = (Partner) mr.getUser();
            if (partner != null) {
                Optional<PartnershipContract> partnershipContract = partner.getDocuments().stream()
                        .filter(d -> d instanceof PartnershipContract)
                        .map(d -> (PartnershipContract) d)
                        .filter(pc -> (pc.getEndDate()).isAfter(LocalDate.now()))
                        .findAny();

                partnershipContract.ifPresent(contract -> mr.setRevenue(mr.getDonations() * contract.getRate()));
            }
        }
        );
        return monthlyReport;
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
     * Create a new monthly report by saving it to the repository.
     *
     * @param monthlyReport The monthly report to be created.
     * @return The created monthly report.
     */
    @Transactional
    public MonthlyReport create(MonthlyReport monthlyReport) {

        return monthlyReportRepository.save(monthlyReport);

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
                    if (updatedMonthlyReport.getType() != null) {
                        monthlyReportToUpdate.setType(updatedMonthlyReport.getType());
                    }
                    if (updatedMonthlyReport.getGDriveLink() != null) {
                        monthlyReportToUpdate.setGDriveLink(updatedMonthlyReport.getGDriveLink());
                    }
                    if (updatedMonthlyReport.getCreationDate() != null) {
                        monthlyReportToUpdate.setCreationDate(updatedMonthlyReport.getCreationDate());
                    }
                    if (updatedMonthlyReport.getStartDate() != null) {
                        monthlyReportToUpdate.setStartDate(updatedMonthlyReport.getStartDate());
                    }
                    if (updatedMonthlyReport.getEndDate() != null) {
                        monthlyReportToUpdate.setEndDate(updatedMonthlyReport.getEndDate());
                    }
                    if (updatedMonthlyReport.getViewers() != null) {
                        monthlyReportToUpdate.setViewers(updatedMonthlyReport.getViewers());
                    }
                    if (updatedMonthlyReport.getHoursWatched() != null) {
                        monthlyReportToUpdate.setHoursWatched(updatedMonthlyReport.getHoursWatched());
                    }
                    if (updatedMonthlyReport.getDonations() != null) {
                        monthlyReportToUpdate.setDonations(updatedMonthlyReport.getDonations());
                    }
                    if (updatedMonthlyReport.getRevenue() != null) {
                        monthlyReportToUpdate.setDonations(updatedMonthlyReport.getRevenue());
                    }
                    return monthlyReportRepository.save(monthlyReportToUpdate);
                })
                .orElse(null);

    }

}
