package com.syncstate.probase.bills.BillerService.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ValidateOTPRequest {
    @NotBlank(message = "One-Time Password must be provided")
    private String otp;
    @NotNull(message = "Last name must be provided")
    private Long userId;
}
