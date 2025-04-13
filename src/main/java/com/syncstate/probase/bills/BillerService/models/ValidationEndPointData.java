package com.syncstate.probase.bills.BillerService.models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syncstate.probase.bills.BillerService.deserializers.TimestampDeserializer;
import com.syncstate.probase.bills.BillerService.serializer.JsonDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "validation_end_point_data")
public class ValidationEndPointData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long validationEndPointDataId;

    @Column(name = "validationEndPointId", unique = false, nullable = false)
    private Long validationEndPointId;

    @Column(name = "serviceProviderId", unique = false, nullable = false)
    private Long serviceProviderId;

    @Column(name = "serviceId", unique = false, nullable = false)
    private Long serviceId;

    @Column(name = "endPointDataKey", unique = false, nullable = false)
    private String endPointDataKey;

    @Column(name = "endPointDataKeyValueFromServiceProviderFieldId", nullable = true)
    private Long endPointDataKeyValueFromServiceProviderFieldId;

    @Column(name = "endPointDataKeyValueNotFromServiceProviderFieldId", unique = false, nullable = true)
    private String endPointDataKeyValueNotFromServiceProviderFieldId;


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
