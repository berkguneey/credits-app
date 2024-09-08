package com.project.credits.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PayInstallmentRequest {
    @NotNull(message = "User ID cannot be null.")
    private Long userId;
    @NotNull(message = "Installment ID cannot be null.")
    private Integer installmentId;
    @NotNull(message = "Amount cannot be null.")
    private BigDecimal amount;
}
