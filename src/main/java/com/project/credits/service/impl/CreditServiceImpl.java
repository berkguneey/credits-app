package com.project.credits.service.impl;

import com.project.credits.entity.Credit;
import com.project.credits.entity.Installment;
import com.project.credits.entity.Payment;
import com.project.credits.entity.User;
import com.project.credits.enums.CreditStatusEnum;
import com.project.credits.enums.InstallmentStatusEnum;
import com.project.credits.mapper.CreditMapper;
import com.project.credits.repository.CreditRepository;
import com.project.credits.request.CreateCreditRequest;
import com.project.credits.response.CreditResponse;
import com.project.credits.response.CreditResponseWithPaging;
import com.project.credits.service.CreditService;
import com.project.credits.service.InstallmentService;
import com.project.credits.service.UserService;
import com.project.credits.specification.CreditFiltersSpecification;
import com.project.credits.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private static final long DAYS_IN_MONTH = 30L;
    private final CreditRepository creditRepository;
    private final UserService userService;
    private final InstallmentService installmentService;
    private final CreditMapper creditMapper;

    @Override
    public CreditResponse createCredit(CreateCreditRequest request) {
        User user = userService.findUserById(request.getUserId());

        Credit credit = creditMapper.toCredit(request);
        credit.setUser(user);
        credit.setStatus(CreditStatusEnum.ACTIVE);

        List<Installment> installments = createInstallments(request.getAmount(), request.getInstallmentCount(), credit);
        credit.setInstallments(installments);

        credit = creditRepository.save(credit);
        return creditMapper.toCreditResponse(credit);
    }

    private List<Installment> createInstallments(BigDecimal creditAmount, int installmentCount, Credit credit) {
        BigDecimal installmentAmount = creditAmount.divide(BigDecimal.valueOf(installmentCount), 2, RoundingMode.HALF_UP);
        BigDecimal remainder = creditAmount.subtract(installmentAmount.multiply(BigDecimal.valueOf(installmentCount - 1)));

        List<Installment> installments = new ArrayList<>();
        for (int i = 0; i < installmentCount; i++) {
            Installment installment = new Installment();
            installment.setCredit(credit);
            installment.setInstallmentId(i + 1);
            installment.setStatus(InstallmentStatusEnum.PENDING);
            installment.setAmount(i == installmentCount - 1 ? remainder : installmentAmount);
            installment.setDueDate(DateUtil.calculateDueDate(LocalDate.now().plusDays((i + 1) * DAYS_IN_MONTH)));
            installments.add(installment);
        }
        return installments;
    }

    @Override
    public List<CreditResponse> getUserCredits(Long userId) {
        User user = userService.findUserById(userId);
        List<Credit> credits = creditRepository.findByUser(user);
        return creditMapper.toCreditResponse(credits);
    }

    @Override
    public CreditResponseWithPaging getUserCreditsWithFilter(Long userId, CreditStatusEnum status, LocalDate date, Pageable pageable) {
        User user = userService.findUserById(userId);

        LocalDateTime startOfDay = null;
        LocalDateTime endOfDay = null;

        if (date != null) {
            startOfDay = date.atStartOfDay();
            endOfDay = date.atTime(LocalTime.MAX);
        }

        Specification<Credit> spec = new CreditFiltersSpecification(user.getId(), status, startOfDay, endOfDay);
        Page<Credit> credits = creditRepository.findAll(spec, pageable);

        CreditResponseWithPaging creditResponseWithPaging = new CreditResponseWithPaging();
        creditResponseWithPaging.setCredits(creditMapper.toCreditResponse(credits.getContent()));
        creditResponseWithPaging.setTotalElements(credits.getTotalElements());
        creditResponseWithPaging.setTotalPages(credits.getTotalPages());
        return creditResponseWithPaging;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // Every minute: 0 * * ? * *
    public void calculateLateOverdueFees() {
        List<Installment> overdueInstallments = installmentService.findOverdueInstallments(LocalDate.now());
        for (Installment installment : overdueInstallments) {
            Credit credit = installment.getCredit();
            if (credit != null) {
                int overdueDays = (int) ChronoUnit.DAYS.between(installment.getDueDate(), LocalDate.now());
                BigDecimal totalPaidAmount = installment.getPayments().stream().map(Payment::getPaidAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal remainingAmount = installment.getAmount().add(installment.getOverdueFeeAmount()).subtract(totalPaidAmount);
                BigDecimal overdueFee = calculateOverdueFee(overdueDays, remainingAmount, credit.getInterestRate());
                installment.setOverdueDays(overdueDays);
                installment.setOverdueFeeAmount(installment.getOverdueFeeAmount().add(overdueFee));
                installment.setStatus(InstallmentStatusEnum.OVERDUE);
                credit.setTotalAmount(credit.getTotalAmount().add(overdueFee));
                creditRepository.save(credit);
            }
        }
    }

    private BigDecimal calculateOverdueFee(long daysOverdue, BigDecimal amount, long interestRate) {
        return amount.multiply(BigDecimal.valueOf(interestRate))
                .multiply(BigDecimal.valueOf(daysOverdue))
                .divide(BigDecimal.valueOf(360), RoundingMode.HALF_UP);
    }

}
