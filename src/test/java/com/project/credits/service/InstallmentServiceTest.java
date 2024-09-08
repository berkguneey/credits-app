package com.project.credits.service;

import com.project.credits.entity.Credit;
import com.project.credits.entity.Installment;
import com.project.credits.entity.Payment;
import com.project.credits.entity.User;
import com.project.credits.enums.ErrorMessagesEnum;
import com.project.credits.enums.InstallmentStatusEnum;
import com.project.credits.enums.PaymentStatusEnum;
import com.project.credits.exception.BusinessException;
import com.project.credits.exception.EntityNotFoundException;
import com.project.credits.repository.InstallmentRepository;
import com.project.credits.service.impl.InstallmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstallmentServiceTest {

    @Mock
    private InstallmentRepository installmentRepository;

    @InjectMocks
    private InstallmentServiceImpl installmentService;

    private User mockUser;
    private List<Installment> mockInstallments;
    private Installment mockInstallment;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);

        Installment mockInstallment1 = new Installment();
        Installment mockInstallment2 = new Installment();
        mockInstallments = Arrays.asList(mockInstallment1, mockInstallment2);

        Credit mockCredit = new Credit();
        mockCredit.setUser(mockUser);

        mockInstallment = new Installment();
        mockInstallment.setId(1L);
        mockInstallment.setCredit(mockCredit);
        mockInstallment.setAmount(BigDecimal.valueOf(100));
        mockInstallment.setOverdueDays(1);
        mockInstallment.setOverdueFeeAmount(BigDecimal.valueOf(10));
        mockInstallment.setStatus(InstallmentStatusEnum.PARTIALLY_PAID);

        Payment mockPayment = new Payment();
        mockPayment.setPaidAmount(BigDecimal.valueOf(50));
        mockPayment.setPaymentDate(LocalDateTime.now());
        mockPayment.setStatus(PaymentStatusEnum.COMPLETED);

        mockInstallment.setPayments(new ArrayList<>(List.of(mockPayment)));
    }

    @Test
    void payInstallment_whenValidPayment_thenUpdateInstallmentAndSave() {
        Long userId = mockUser.getId();
        Integer installmentId = mockInstallment.getInstallmentId();
        BigDecimal paymentAmount = BigDecimal.valueOf(60);

        when(installmentRepository.findByInstallmentId(installmentId)).thenReturn(Optional.of(mockInstallment));
        when(installmentRepository.save(any(Installment.class))).thenReturn(mockInstallment);

        installmentService.payInstallment(userId, installmentId, paymentAmount);

        assertEquals(2, mockInstallment.getPayments().size());

        Payment newPayment = mockInstallment.getPayments().get(1);
        assertEquals(paymentAmount, newPayment.getPaidAmount());
        assertEquals(PaymentStatusEnum.COMPLETED, newPayment.getStatus());

        assertEquals(InstallmentStatusEnum.PAID, mockInstallment.getStatus());

        verify(installmentRepository, times(1)).findByInstallmentId(installmentId);
        verify(installmentRepository, times(1)).save(mockInstallment);
    }

    @Test
    void findOverdueInstallments_whenCalledWithSpecificDate_thenReturnsOverdueInstallments() {
        LocalDate date = LocalDate.now();
        when(installmentRepository.findOverdueInstallments(date)).thenReturn(mockInstallments);

        List<Installment> result = installmentService.findOverdueInstallments(date);

        verify(installmentRepository, times(1)).findOverdueInstallments(date);

        assertNotNull(result);
    }

    @Test
    void payInstallment_whenInstallmentAlreadyPaid_thenThrowBusinessException() {
        mockInstallment.setStatus(InstallmentStatusEnum.PAID);

        Long userId = mockUser.getId();
        Integer installmentId = mockInstallment.getInstallmentId();
        BigDecimal paymentAmount = BigDecimal.valueOf(50);

        when(installmentRepository.findByInstallmentId(installmentId)).thenReturn(Optional.of(mockInstallment));

        BusinessException exception = assertThrows(BusinessException.class, () -> installmentService.payInstallment(userId, installmentId, paymentAmount));

        assertEquals(ErrorMessagesEnum.INSTALLMENT_ALREADY_PAID_BEFORE.getMessage(), exception.getMessage());
    }

    @Test
    void payInstallment_whenInstallmentNotBelongsToUser_thenThrowBusinessException() {
        User user = new User();
        user.setId(2L);

        mockInstallment.getCredit().setUser(user);

        Long userId = mockUser.getId();
        Integer installmentId = mockInstallment.getInstallmentId();
        BigDecimal paymentAmount = BigDecimal.valueOf(50);

        when(installmentRepository.findByInstallmentId(installmentId)).thenReturn(Optional.of(mockInstallment));

        BusinessException exception = assertThrows(BusinessException.class, () -> installmentService.payInstallment(userId, installmentId, paymentAmount));

        assertEquals(ErrorMessagesEnum.INSTALLMENT_NOT_BELONG_TO_THE_USER.getMessage(), exception.getMessage());
    }

    @Test
    void payInstallment_whenOverpaymentDetected_thenThrowBusinessException() {
        BigDecimal paymentAmount = BigDecimal.valueOf(200);

        when(installmentRepository.findByInstallmentId(mockInstallment.getInstallmentId())).thenReturn(Optional.of(mockInstallment));

        BusinessException exception = assertThrows(BusinessException.class, () ->
                installmentService.payInstallment(mockUser.getId(), mockInstallment.getInstallmentId(), paymentAmount));

        assertEquals(ErrorMessagesEnum.OVERPAYMENT_DETECTED.getMessage(), exception.getMessage());
    }

    @Test
    void payInstallment_whenInstallmentNotFound_thenThrowEntityNotFoundException() {
        Long userId = mockUser.getId();
        Integer installmentId = 999;
        BigDecimal paymentAmount = BigDecimal.valueOf(50);

        when(installmentRepository.findByInstallmentId(installmentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                installmentService.payInstallment(userId, installmentId, paymentAmount));

        assertEquals(ErrorMessagesEnum.INSTALLMENT_NOT_FOUND.getMessage(), exception.getMessage());
    }

}
