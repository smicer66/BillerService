package com.syncstate.probase.bills.BillerService.models.requests;

import com.syncstate.probase.bills.BillerService.models.ValidationEndPoint;
import com.syncstate.probase.bills.BillerService.models.ValidationEndPointData;
import com.syncstate.probase.bills.BillerService.models.ValidationEndPointHeader;
import com.syncstate.probase.bills.BillerService.models.dto.ValidationEndPointDTO;
import com.syncstate.probase.bills.BillerService.models.dto.ValidationEndPointDataDTO;
import com.syncstate.probase.bills.BillerService.models.dto.ValidationEndPointHeaderDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CreateValidationEndPointRequest {


    private Long serviceProviderId;

    private Long serviceId;

    private Long serviceProviderFieldId;

    private List<ValidationEndPointDataDTO> validationEndPointData;

    private List<ValidationEndPointHeaderDTO> validationEndPointHeader;

    private ValidationEndPointDTO validationEndPoint;
}
