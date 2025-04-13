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

@Entity
@Getter
@Setter
@Table(name = "enumerated_field_values")
public class EnumeratedFieldValue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enumeratedFieldValueId;

    @Column(name = "serviceProviderFieldId", nullable = true)
    private Long serviceProviderFieldId;

    @Column(name = "value", unique = true, nullable = false)
    private String optionValue;

    @Column(name = "optionTitle", unique = true, nullable = false)
    private String optionTitle;

    @Column(name = "status", nullable = true)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "serialNo", nullable = false)
    private Integer serialNo;

    @Column(name = "serviceProviderId", nullable = true)
    private Long serviceProviderId;

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
