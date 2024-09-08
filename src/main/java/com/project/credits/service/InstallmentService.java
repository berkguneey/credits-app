package com.project.credits.service;

import com.project.credits.entity.Installment;
import com.project.credits.exception.BusinessException;
import com.project.credits.exception.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface InstallmentService {

    /**
     * Retrieves all overdue installments based on the given date.
     *
     * @param date the date to check against installment due dates
     * @return a list of overdue installments
     */
    List<Installment> findOverdueInstallments(LocalDate date);

    /**
     * Processes the payment for a specific installment of a credit belonging to the specified user.
     *
     * @param userId        The ID of the user making the payment.
     * @param installmentId The ID of the installment to be paid.
     * @param amount        The amount of money being paid towards the installment.
     * @throws EntityNotFoundException If the installment is not found or does not belong to the user.
     * @throws BusinessException       If the installment is already paid, if the user does not own the installment,
     *                                 or if an overpayment is detected, a specific error message is thrown.
     */
    void payInstallment(Long userId, Integer installmentId, BigDecimal amount);

}
