package org.documentoviscode.splashyapi.controllers;


import org.documentoviscode.splashyapi.data.requests.MonthlyReportDTO;
import org.documentoviscode.splashyapi.domain.MonthlyReport;
import org.documentoviscode.splashyapi.services.MonthlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing monthly report-related operations.
 */
@RestController
@RequestMapping("/monthlyReports")
public class MonthlyReportController {
    private final MonthlyReportService monthlyReportService;

    /**
     * Constructor for the MonthlyReportController class.
     *
     * @param monthlyReportService The service for managing monthly report-related operations.
     */
    @Autowired
    public MonthlyReportController(MonthlyReportService monthlyReportService) {
        this.monthlyReportService = monthlyReportService;
    }

    /**
     * Retrieve a list of all monthly reports.
     *
     * @return ResponseEntity containing the list of all monthly report entities.
     */
    @GetMapping("")
    public ResponseEntity<List<MonthlyReport>> getAllMonthlyReports() {
        return ResponseEntity.ok(monthlyReportService.findAll());
    }

    /**
     * Retrieve a monthly report by a specified ID.
     *
     * @param id The ID of the monthly report to retrieve.
     * @return ResponseEntity containing the monthly report or a "not found" response if the report is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MonthlyReport> getMonthlyReport(@PathVariable("id") long id) {
        Optional<MonthlyReport> monthlyReport = monthlyReportService.findMonthlyReportById(id);
        return monthlyReport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Update an existing monthly report based on its ID.
     *
     * @param id                   The ID of the monthly report to be updated.
     * @param updatedMonthlyReport The updated monthly report data.
     * @return A ResponseEntity containing the updated monthly report if successful, or a not found status if the monthly report with the specified ID is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MonthlyReport> updateMonthlyReport(@PathVariable Long id, @RequestBody MonthlyReportDTO updatedMonthlyReport) {
        MonthlyReport updated = monthlyReportService.updateMonthlyReport(id, updatedMonthlyReport);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
