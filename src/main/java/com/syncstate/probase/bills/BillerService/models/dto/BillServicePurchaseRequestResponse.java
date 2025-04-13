package com.syncstate.probase.bills.BillerService.models.dto;


import com.syncstate.probase.bills.BillerService.models.BillServicePurchaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BillServicePurchaseRequestResponse {

    private List<BillServicePurchaseRequest> billServicePurchaseRequestList;
    private Double totalAmountToPay;
    private String requestRefNo;
}
