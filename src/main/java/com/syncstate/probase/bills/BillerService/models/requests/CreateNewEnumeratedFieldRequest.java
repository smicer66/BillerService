package com.syncstate.probase.bills.BillerService.models.requests;


import com.syncstate.probase.bills.BillerService.models.EnumeratedFieldValueData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateNewEnumeratedFieldRequest {

    private List<EnumeratedFieldValueData> enumeratedFieldValueList;

    private Long serviceProviderFieldId;

    private Long serviceProviderId;
}
