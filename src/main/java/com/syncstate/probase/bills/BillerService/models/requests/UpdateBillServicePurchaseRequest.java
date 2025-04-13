package com.syncstate.probase.bills.BillerService.models.requests;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;


@Getter
@Setter
public class UpdateBillServicePurchaseRequest implements Serializable {


    private String orderRefNo;

    private String switchTransactionRef;

    private String status;

    private Double transactionAmount;

    private String domain;

    private String gatewayResponse;

    private LocalDateTime switchPaymentDate;

    private String channel;

    private String currency;

    private String paymentIPAddress;

    private Double fees;

    private String customerEmail;

    private String customerPhone;

    private String bin;

    private String last4;

    private String expMonth;

    private String expYear;

    private String cardType;

    private String bank;

    private String brand;

    private String accountName;

    private String billPayerMobile;

    private String billPayerEmailAddress;
}
