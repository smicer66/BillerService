package com.syncstate.probase.bills.BillerService.models.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoggedInUserDTO {

    private String fullName;
    private String token;

}
