package org.documentoviscode.splashyapi.utility;

import org.documentoviscode.splashyapi.domain.MonthlyReport;
import org.documentoviscode.splashyapi.domain.Partner;
import org.documentoviscode.splashyapi.domain.PartnershipContract;

import java.time.LocalDate;
import java.util.Optional;

public class RevenueCalculator {
    static public Double calculateRevenue(MonthlyReport monthlyReport) {
        Partner partner = (Partner) monthlyReport.getUser();
        if (partner != null) {
            Optional<PartnershipContract> partnershipContract = partner.getDocuments().stream()
                    .filter(d -> d instanceof PartnershipContract)
                    .map(d -> (PartnershipContract) d)
                    .filter(pc -> (pc.getEndDate()).isAfter(LocalDate.now()))
                    .findAny();

            return partnershipContract.map(contract -> monthlyReport.getDonations() * contract.getRate()).orElse(0.0);
        }
        else return 0.0;
    }
}
