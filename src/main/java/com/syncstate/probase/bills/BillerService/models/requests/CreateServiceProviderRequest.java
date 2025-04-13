package com.syncstate.probase.bills.BillerService.models.requests;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateServiceProviderRequest {

    private String serviceProviderName;

    private String serviceProviderNote;

    private String serviceProviderFriendlyUrl;

    private String serviceProviderLogo;

    private String serviceEndpoint;

    private Long serviceId;
}
