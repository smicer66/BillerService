package com.syncstate.probase.bills.BillerService.repositories;


import com.syncstate.probase.bills.BillerService.models.BillService;
import com.syncstate.probase.bills.BillerService.models.BillServicePurchaseRequest;
import com.syncstate.probase.bills.BillerService.models.BillServicePurchaseRequestFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillServicePurchaseRequestFileRepository extends JpaRepository<BillServicePurchaseRequestFile, Long> {

    @Query("Select bt from BillServicePurchaseRequestFile bt where bt.deletedAt IS NULL")
    List<BillServicePurchaseRequestFile> getBillServicePurchaseRequestFile();

    @Query("Select bt from BillServicePurchaseRequestFile bt where bt.deletedAt IS NULL AND bt.billServicePurchaseRequestFileId = :billServicePurchaseRequestFileId")
    BillServicePurchaseRequestFile getBillServicePurchaseRequestFileById(Long billServicePurchaseRequestFileId);

}
