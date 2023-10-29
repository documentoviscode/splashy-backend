package org.documentoviscode.splashyapi.services;

import org.documentoviscode.splashyapi.domain.MonthlyReport;
import org.documentoviscode.splashyapi.repositories.MonthlyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonthlyReportService {

    private final MonthlyReportRepository monthlyReportRepository;


    @Autowired
    public MonthlyReportService(MonthlyReportRepository monthlyReportRepository) {
        this.monthlyReportRepository = monthlyReportRepository;
    }


    public Optional<MonthlyReport> findMonthlyReportById(Long id) {
        return monthlyReportRepository.findById(id);
    }



    public List<MonthlyReport> findAll() {
        return monthlyReportRepository.findAll();
    }
}
