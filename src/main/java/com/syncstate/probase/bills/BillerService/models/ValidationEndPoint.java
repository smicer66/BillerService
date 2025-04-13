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
@Table(name = "validation_end_points")
public class ValidationEndPoint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long validationEndPointId;

    @Column(name = "serviceProviderFieldId", unique = true, nullable = false)
    private Long serviceProviderFieldId;

    @Column(name = "serviceProviderId", unique = false, nullable = false)
    private Long serviceProviderId;

    @Column(name = "serviceId", unique = false, nullable = false)
    private Long serviceId;

    @Column(name = "endPointUrl", unique = false, nullable = false)
    private String endPointUrl;

    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "contentType", nullable = true)
    private String contentType;

    @Column(name = "responseKeyForIsValdated", nullable = true)
    private String responseKeyForIsValdated;

    @Column(name = "responseKeyForIsValdatedMessage", nullable = true)
    private String responseKeyForIsValdatedMessage;

    @Column(name = "responseKeyDataToDisplay", nullable = true)
    private String responseKeyDataToDisplay;


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
