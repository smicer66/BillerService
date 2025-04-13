package com.syncstate.probase.bills.BillerService.models.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReceiptTransactionDTO {

    private Long transactionId;
    private String orderRefNo;
    private LocalDateTime transactionDate;
    private Double totalAmount;

    public ReceiptTransactionDTO(Long transactionId, String orderRefNo, LocalDateTime transactionDate, Double totalAmount)
    {
        this.transactionId = transactionId;
        this.orderRefNo = orderRefNo;
        this.transactionDate = transactionDate;
        this.totalAmount = totalAmount;
    }
}
