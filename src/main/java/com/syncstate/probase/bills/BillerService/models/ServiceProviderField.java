package com.syncstate.probase.bills.BillerService.models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syncstate.probase.bills.BillerService.deserializers.TimestampDeserializer;
import com.syncstate.probase.bills.BillerService.enums.ServiceProviderFieldType;
import com.syncstate.probase.bills.BillerService.enums.Status;
import com.syncstate.probase.bills.BillerService.serializer.JsonDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "service_provider_fields")
public class ServiceProviderField implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceProviderFieldId;

    @Column(name = "ServiceProviderId", nullable = true)
    private Long ServiceProviderId;

    @Column(name = "serviceId", nullable = true)
    private Long serviceId;

    @Column(name = "serviceProviderFieldName", nullable = false)
    private String serviceProviderFieldName;

    @Column(name = "serviceProviderFieldTitle", nullable = false)
    private String serviceProviderFieldTitle;

    @Column(name = "serviceProviderFieldType", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceProviderFieldType serviceProviderFieldType;

    @Column(name = "serviceValidationEndpoint", nullable = true)
    private String serviceValidationEndpoint;

    @Column(name = "serviceFieldDataEndpoint", nullable = true)
    private String serviceFieldDataEndpoint;

    @Column(name = "serviceFieldDataSourceField", nullable = true)
    private String serviceFieldDataSourceField;

    @Column(name = "serviceFieldDataSourceFieldKey", nullable = true)
    private String serviceFieldDataSourceFieldKey;

    @Column(name = "isRequired", nullable = true)
    private Boolean isRequired;

    @Column(name = "isBillAmount", nullable = false)
    private Boolean isBillAmount;

    @Column(name = "isHidden", nullable = true)
    private Boolean isHidden;

    @Column(name = "isEmailRecipient", nullable = true)
    private Boolean isEmailRecipient;

    @Column(name = "isSmsRecipient", nullable = true)
    private Boolean isSmsRecipient;

    @Column(name = "minSize", nullable = true)
    private Integer minSize;

    @Column(name = "maxSize", nullable = true)
    private Integer maxSize;

    @Column(name = "serviceTypeEnumeratedValues", nullable = true)
    private String serviceTypeEnumeratedValues;

    @Column(name = "serialNo", nullable = false)
    private Integer serialNo;

    @Column(name = "displayBasedOnServiceField", nullable = true)
    private String displayBasedOnServiceField;

    @Column(name = "displayBasedOnServiceFieldValue", nullable = true)
    private String displayBasedOnServiceFieldValue;

    @Column(name = "status", nullable = true)
    @Enumerated(EnumType.STRING)
    private Status serviceProviderFieldStatus;

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
