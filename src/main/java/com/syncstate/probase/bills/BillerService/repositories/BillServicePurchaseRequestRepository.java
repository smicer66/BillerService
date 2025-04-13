package com.syncstate.probase.bills.BillerService.repositories;


import com.syncstate.probase.bills.BillerService.models.BillService;
import com.syncstate.probase.bills.BillerService.models.BillServicePurchaseRequest;
import com.syncstate.probase.bills.BillerService.models.dto.ReceiptDataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillServicePurchaseRequestRepository extends JpaRepository<BillServicePurchaseRequest, Long> {

    @Query("Select bt from BillServicePurchaseRequest bt where bt.deletedAt IS NULL")
    List<BillService> getBillServicePurchaseRequests();

    @Query("Select bt from BillServicePurchaseRequest bt where bt.deletedAt IS NULL AND bt.billServiceId = :billServiceId")
    BillService getBillServiceById(Long billServiceId);

    @Query("Select bt from BillServicePurchaseRequest bt where bt.deletedAt IS NULL AND bt.requestRefNo = :requestRefNo")
    List<BillServicePurchaseRequest> getBillServicePurchaseRequestByRequestRefNo(String requestRefNo);

    @Query("Select new com.syncstate.probase.bills.BillerService.models.dto.ReceiptDataDTO(bt.dataFields, bt.billAmount) from BillServicePurchaseRequest bt where bt.deletedAt IS NULL AND bt.transactionId = :transactionId")
    List<ReceiptDataDTO> getBillServicePurchaseRequestByTransactionId(Long transactionId);
}
