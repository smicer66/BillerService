package com.syncstate.probase.bills.BillerService.models.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateServiceRequest {

    private String billTypeName;

    private String billTypeFriendlyUrl;

    private String billTypeLogo;

    private String menuIcon;
}
