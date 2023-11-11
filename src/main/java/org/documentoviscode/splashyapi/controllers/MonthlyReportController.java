package org.documentoviscode.splashyapi.controllers;


import org.documentoviscode.splashyapi.data.requests.MonthlyReportDTO;
import org.documentoviscode.splashyapi.domain.MonthlyReport;
import org.documentoviscode.splashyapi.domain.Subscription;
import org.documentoviscode.splashyapi.domain.User;
import org.documentoviscode.splashyapi.dto.CreateMonthlyReportDto;
import org.documentoviscode.splashyapi.dto.CreateSubscriptionDto;
import org.documentoviscode.splashyapi.services.MonthlyReportService;
import org.documentoviscode.splashyapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing monthly report-related operations.
 */
@RestController
@RequestMapping("/monthlyReports")
public class MonthlyReportController {
    private final MonthlyReportService monthlyReportService;
    private final UserService userService;

    /**
     * Constructor for the MonthlyReportController class.
     *
     * @param monthlyReportService The service for managing monthly report-related operations.
     */
    @Autowired
    public MonthlyReportController(MonthlyReportService monthlyReportService, UserService userService) {
        this.monthlyReportService = monthlyReportService;
        this.userService = userService;
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
     * Create a new MonthlyReport for a specified user.
     *
     * @param monthlyReportDto The monthly report DTO record to create monthly report entity.
     * @param userId          The ID of the user for whom the monthly record is created.
     * @return ResponseEntity containing the created monthly record and HTTP status.
     */
    @PostMapping
    public ResponseEntity<MonthlyReport> createMonthlyReport(@RequestBody CreateMonthlyReportDto monthlyReportDto, @RequestParam Long userId )
    {
        Optional<User> userOptional = userService.findUserById(userId);

        if(userOptional.isPresent())
        {
            MonthlyReport newMonthlyReport = CreateMonthlyReportDto
                    .dtoToEntityMapper()
                    .apply(monthlyReportDto);

            newMonthlyReport.setUser(userOptional.get());
            MonthlyReport createdMonthlyReport = monthlyReportService.create(newMonthlyReport);
            if(createdMonthlyReport!=null)
            {
                return new ResponseEntity<>(createdMonthlyReport, HttpStatus.CREATED);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
