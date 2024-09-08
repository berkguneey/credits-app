package com.project.credits.service.impl;

import com.project.credits.entity.Credit;
import com.project.credits.entity.Installment;
import com.project.credits.entity.Payment;
import com.project.credits.enums.CreditStatusEnum;
import com.project.credits.enums.ErrorMessagesEnum;
import com.project.credits.enums.InstallmentStatusEnum;
import com.project.credits.enums.PaymentStatusEnum;
import com.project.credits.exception.BusinessException;
import com.project.credits.exception.EntityNotFoundException;
import com.project.credits.repository.InstallmentRepository;
import com.project.credits.service.InstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentRepository installmentRepository;

    @Override
    public List<Installment> findOverdueInstallments(LocalDate date) {
        return installmentRepository.findOverdueInstallments(date);
    }

    @Transactional
    @Override
    public void payInstallment(Long userId, Integer installmentId, BigDecimal amount) {
        Installment installment = findByInstallmentId(installmentId);

        validateInstallment(installment, userId);

        BigDecimal totalPaidAmount = installment.getPayments().stream().map(Payment::getPaidAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal remainingAmount = installment.getAmount().add(installment.getOverdueFeeAmount()).subtract(totalPaidAmount);

        validatePaymentAmount(amount, remainingAmount);

        Payment payment = new Payment();
        payment.setInstallment(installment);
        payment.setPaidAmount(amount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(PaymentStatusEnum.COMPLETED);
        installment.getPayments().add(payment);

        updateInstallmentStatus(installment, totalPaidAmount.add(amount));
        installmentRepository.save(installment);

        updateCreditStatusIfNeeded(installment.getCredit());
    }

    private void validateInstallment(Installment installment, Long userId) {
        if (InstallmentStatusEnum.PAID.equals(installment.getStatus())) {
            throw new BusinessException(ErrorMessagesEnum.INSTALLMENT_ALREADY_PAID_BEFORE);
        }
        if (!installment.getCredit().getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorMessagesEnum.INSTALLMENT_NOT_BELONG_TO_THE_USER);
        }
    }

    private void validatePaymentAmount(BigDecimal amount, BigDecimal remainingAmount) {
        if (amount.compareTo(remainingAmount) > 0) {
            throw new BusinessException(ErrorMessagesEnum.OVERPAYMENT_DETECTED);
        }
    }

    private void updateInstallmentStatus(Installment installment, BigDecimal totalPaidAmountWithNewPayment) {
        if (totalPaidAmountWithNewPayment.compareTo(installment.getAmount().add(installment.getOverdueFeeAmount())) == 0) {
            installment.setStatus(InstallmentStatusEnum.PAID);
        } else if (totalPaidAmountWithNewPayment.compareTo(installment.getAmount().add(installment.getOverdueFeeAmount())) < 0) {
            installment.setStatus(InstallmentStatusEnum.PARTIALLY_PAID);
        }
    }

    private void updateCreditStatusIfNeeded(Credit credit) {
        boolean allInstallmentsPaid = credit.getInstallments().stream().allMatch(i -> InstallmentStatusEnum.PAID.equals(i.getStatus()));
        if (allInstallmentsPaid) {
            credit.setStatus(CreditStatusEnum.PAID_OFF);
        }
    }

    private Installment findByInstallmentId(Integer installmentId) {
        return installmentRepository.findByInstallmentId(installmentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessagesEnum.INSTALLMENT_NOT_FOUND));
    }

}
