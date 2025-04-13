package com.syncstate.probase.bills.BillerService.models.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ValidationEndPointDTO {

    private String endPointUrl;

    private String method;

    private String contentType;
}
