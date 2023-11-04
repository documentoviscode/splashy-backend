package org.documentoviscode.splashyapi.repositories;

import org.documentoviscode.splashyapi.domain.PartnershipContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnershipContractRepository extends JpaRepository<PartnershipContract, Long> {
}
