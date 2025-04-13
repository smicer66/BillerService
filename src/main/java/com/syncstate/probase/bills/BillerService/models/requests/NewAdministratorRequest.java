package com.syncstate.probase.bills.BillerService.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Getter
@Setter
public class NewAdministratorRequest {
    @NotBlank(message = "First name must be provided")
    private String fullName;
    @NotBlank(message = "Mobile number must be provided")
    private String mobileNumber;
    @NotBlank(message = "Email address must be provided")
    private String emailAddress;

}
