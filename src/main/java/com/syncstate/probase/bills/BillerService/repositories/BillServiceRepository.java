package com.syncstate.probase.bills.BillerService.repositories;


import com.syncstate.probase.bills.BillerService.models.BillService;
import com.syncstate.probase.bills.BillerService.models.ServiceProvider;
import com.syncstate.probase.bills.BillerService.models.ServiceProviderField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillServiceRepository extends JpaRepository<BillService, Long> {

    @Query("Select bt from BillService bt where bt.deletedAt IS NULL ORDER BY bt.billServiceId ASC")
    List<BillService> getBillServices();

    @Query("Select bt from BillService bt where bt.deletedAt IS NULL AND bt.billServiceId = :billServiceId ORDER BY bt.billServiceId ASC")
    BillService getBillServiceById(Long billServiceId);
}
