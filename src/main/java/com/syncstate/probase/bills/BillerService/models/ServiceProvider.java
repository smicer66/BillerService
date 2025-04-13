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
@Table(name = "service_providers")
public class ServiceProvider implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceProviderId;

    @Column(name = "serviceId", nullable = true)
    private Long serviceId;

    @Column(name = "serviceProviderName", unique = false, nullable = false)
    private String serviceProviderName;

    @Column(name = "serviceFriendlyUrl", unique = false, nullable = true)
    private String serviceFriendlyUrl;

    @Column(name = "serviceProviderNote", unique = false, nullable = false)
    private String serviceProviderNote;

    @Column(name = "serviceProviderFriendlyUrl", unique = false, nullable = false)
    private String serviceProviderFriendlyUrl;

    @Column(name = "serviceProviderLogo", nullable = true)
    private String serviceProviderLogo;

    @Column(name = "serviceEndpoint", nullable = false)
    private String serviceEndpoint;

    @Column(name = "status", nullable = true)
    @Enumerated(EnumType.STRING)
    private Status serviceProviderStatus;

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
