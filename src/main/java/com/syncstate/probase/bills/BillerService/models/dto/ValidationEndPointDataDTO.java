package com.syncstate.probase.bills.BillerService.models.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ValidationEndPointDataDTO {

    private String endPointDataKey;

    private Long endPointDataKeyValueFromServiceProviderFieldId;

    private String endPointDataKeyValueNotFromServiceProviderFieldId;
}
