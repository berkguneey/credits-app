package com.project.credits.dto;

import com.project.credits.enums.CreditStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreditDto {
    private Long id;
    private BigDecimal principalAmount;
    private BigDecimal totalAmount;
    private Long interestRate;
    private CreditStatusEnum status;
    private List<InstallmentDto> installments;
}
