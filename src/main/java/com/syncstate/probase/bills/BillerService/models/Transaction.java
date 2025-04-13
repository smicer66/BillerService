package com.syncstate.probase.bills.BillerService.models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syncstate.probase.bills.BillerService.deserializers.TimestampDeserializer;
import com.syncstate.probase.bills.BillerService.serializer.JsonDateTimeSerializer;
import com.syncstate.probase.bills.BillerService.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;


    @Column(name = "switchTransactionRef", nullable = true)
    private String switchTransactionRef;

    @Column(name = "orderRefNo", nullable = false)
    private String orderRefNo;

    @Column(name = "sourceIPAddress", nullable = false)
    private String sourceIPAddress;

    @Column(name = "transactionAmount", nullable = false)
    private Double transactionAmount;

    @Column(name = "status", nullable = true)
    @Enumerated(EnumType.STRING)
    private Status transactionStatus;

    @Column(name = "domain", nullable = false)
    private String domain;

    @Column(name = "gatewayResponse", nullable = true)
    private String gatewayResponse;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(name = "switchPaymentDate", nullable = false)
    private LocalDateTime switchPaymentDate;

    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "paymentIPAddress", nullable = false)
    private String paymentIPAddress;

    @Column(name = "fees", nullable = true)
    private Double fees;

    @Column(name = "customerEmail", nullable = true)
    private String customerEmail;

    @Column(name = "customerPhone", nullable = true)
    private String customerPhone;

    @Column(name = "bin", nullable = true)
    private String bin;

    @Column(name = "last4", nullable = true)
    private String last4;

    @Column(name = "expMonth", nullable = true)
    private String expMonth;

    @Column(name = "expYear", nullable = true)
    private String expYear;

    @Column(name = "cardType", nullable = true)
    private String cardType;

    @Column(name = "bank", nullable = true)
    private String bank;

    @Column(name = "brand", nullable = true)
    private String brand;

    @Column(name = "accountName", nullable = true)
    private String accountName;

    @Column(name = "billPayerEmailAddress", nullable = true)
    private String billPayerEmailAddress;

    @Column(name = "billPayerMobile", nullable = true)
    private String billPayerMobile;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(nullable= false)
    private LocalDateTime createdAt;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(nullable= true)
    private LocalDateTime deletedAt;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(nullable= false)
    private LocalDateTime updatedAt;

}
