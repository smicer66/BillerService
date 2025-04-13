package com.syncstate.probase.bills.BillerService.models.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReceiptDTO {

    private ReceiptTransactionDTO receiptTransactionDTO;
    private List<ReceiptDataDTO> receiptDataDTO;

}
