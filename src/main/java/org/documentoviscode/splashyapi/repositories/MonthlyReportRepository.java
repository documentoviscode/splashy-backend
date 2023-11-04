package org.documentoviscode.splashyapi.repositories;

import org.documentoviscode.splashyapi.domain.MonthlyReport;
import org.documentoviscode.splashyapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing MonthlyReport entities.
 */
@Repository
public interface MonthlyReportRepository extends JpaRepository<MonthlyReport, Long> {
}
