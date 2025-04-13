package com.syncstate.probase.bills.BillerService.models.dto;


import com.syncstate.probase.bills.BillerService.models.BillServicePurchaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReceiptDataDTO {

    private String dataFields;
    private Double billAmount;

    public ReceiptDataDTO(String dataFields, Double billAmount)
    {
        this.dataFields = dataFields;
        this.billAmount = billAmount;
    }
}
