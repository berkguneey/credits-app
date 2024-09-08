package com.project.credits.response;

import com.project.credits.dto.InstallmentDto;
import com.project.credits.enums.CreditStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreditResponse {
    private Long id;
    private BigDecimal principalAmount;
    private CreditStatusEnum status;
    private List<InstallmentDto> installments;
}
