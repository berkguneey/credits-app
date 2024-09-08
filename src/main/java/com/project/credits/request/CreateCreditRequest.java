package com.project.credits.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateCreditRequest {
    @NotNull(message = "User ID cannot be null.")
    private Long userId;
    @NotNull(message = "Amount cannot be null.")
    private BigDecimal amount;
    @NotNull(message = "Interest Rate cannot be null.")
    private Long interestRate;
    @Min(value = 1, message = "Installment Count must be greater than zero.")
    private int installmentCount;
}
