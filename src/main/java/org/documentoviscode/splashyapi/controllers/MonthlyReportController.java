package org.documentoviscode.splashyapi.controllers;


import org.documentoviscode.splashyapi.domain.MonthlyReport;
import org.documentoviscode.splashyapi.services.MonthlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/monthlyReports")
public class MonthlyReportController {

    private final MonthlyReportService monthlyReportService;


    @Autowired
    public MonthlyReportController(MonthlyReportService monthlyReportService) {
        this.monthlyReportService = monthlyReportService;
    }


    @GetMapping("")
    public ResponseEntity<List<MonthlyReport>> getAllMonthlyReports() {
        return ResponseEntity.ok(monthlyReportService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<MonthlyReport> getMonthlyReport(@PathVariable("id") long id) {
        Optional<MonthlyReport> monthlyReport = monthlyReportService.findMonthlyReportById(id);
        return monthlyReport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
