package com.syncstate.probase.bills.BillerService.models.requests;


import com.syncstate.probase.bills.BillerService.enums.ServiceProviderFieldType;
import com.syncstate.probase.bills.BillerService.models.ValidationEndPoint;
import com.syncstate.probase.bills.BillerService.models.ValidationEndPointData;
import com.syncstate.probase.bills.BillerService.models.ValidationEndPointHeader;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateServiceProviderFieldRequest {

    private String serviceProviderFieldName;

    private String serviceProviderFieldTitle;

    private String serviceProviderFieldType;

    private String serviceValidationEndpoint;

    private String serviceFieldDataEndpoint;

    private String serviceFieldDataSourceField;

    private String serviceFieldDataSourceFieldKey;

    private Boolean isRequired;

    private Boolean isBillAmount;

    private Boolean isHidden;

    private Boolean isEmailRecipient;

    private Boolean isSmsRecipient;

    private Integer minSize;

    private Integer maxSize;

    private String serviceTypeEnumeratedValues;

    private Integer serialNo;

    private String displayBasedOnServiceField;

    private String displayBasedOnServiceFieldValue;

    private Long serviceProviderId;

    private Long serviceId;
}
