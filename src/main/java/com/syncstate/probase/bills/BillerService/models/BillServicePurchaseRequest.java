package com.syncstate.probase.bills.BillerService.models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syncstate.probase.bills.BillerService.deserializers.TimestampDeserializer;
import com.syncstate.probase.bills.BillerService.enums.Status;
import com.syncstate.probase.bills.BillerService.serializer.JsonDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;


@Entity
@Getter
@Setter
@Table(name = "bill_service_purchase_requests")
public class BillServicePurchaseRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BillServicePurchaseRequestId;

    @Column(name = "billServiceId", nullable = false)
    private Long billServiceId;

    @Column(name = "billServiceProviderId", nullable = true)
    private Long billServiceProviderId;

    @Column(name = "dataFields", nullable = false, columnDefinition = "TEXT")
    private String dataFields;

    @Column(name = "billAmount", nullable = false)
    private Double billAmount;

    @Column(name = "requestRefNo", nullable = false)
    private String requestRefNo;

    @Column(name = "sourceIPAddress", nullable = false)
    private String sourceIPAddress;

    @Column(name = "status", nullable = true)
    @Enumerated(EnumType.STRING)
    private Status purchaseRequestStatus;

    @Column(name = "transactionId", nullable = true)
    private Long transactionId;

    @Column(name = "userId", nullable = true)
    private Long userId;

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
