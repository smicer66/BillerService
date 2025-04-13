package com.syncstate.probase.bills.BillerService.models.requests;


import com.syncstate.probase.bills.BillerService.constraints.ValidPassword;
import com.syncstate.probase.bills.BillerService.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "First name must be provided")
    private String fullName;
    @NotBlank(message = "Mobile number must be provided")
    private String mobileNumber;
    @NotBlank(message = "Email address must be provided")
    private String emailAddress;
    @NotBlank(message = "Password must be provided")
    @ValidPassword
    private String password;

}
