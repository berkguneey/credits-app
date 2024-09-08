package com.project.credits.dto;

import com.project.credits.enums.InstallmentStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InstallmentDto {
    private Long id;
    private Integer installmentId;
    private BigDecimal amount;
    private LocalDate dueDate;
    private int overdueDays;
    private BigDecimal overdueFeeAmount;
    private InstallmentStatusEnum status;
}
