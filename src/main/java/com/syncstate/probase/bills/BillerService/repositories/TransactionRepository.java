package com.syncstate.probase.bills.BillerService.repositories;


import com.syncstate.probase.bills.BillerService.models.ServiceProvider;
import com.syncstate.probase.bills.BillerService.models.Transaction;
import com.syncstate.probase.bills.BillerService.models.dto.ReceiptTransactionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("Select bt from Transaction bt where bt.deletedAt IS NULL")
    List<Transaction> getTransactions();

    @Query("Select bt from Transaction bt where bt.deletedAt IS NULL AND bt.transactionId = :transactionId")
    Transaction getTransactionId(Long transactionId);

    @Query("Select new com.syncstate.probase.bills.BillerService.models.dto.ReceiptTransactionDTO(bt.transactionId, bt.orderRefNo, bt.createdAt, bt.transactionAmount) from Transaction bt where bt.deletedAt IS NULL AND bt.orderRefNo = :orderRefNo")
    ReceiptTransactionDTO getReceiptTransactionByOrderNo(String orderRefNo);


}
