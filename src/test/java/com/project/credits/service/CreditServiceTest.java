package com.project.credits.service;

import com.project.credits.entity.Credit;
import com.project.credits.entity.User;
import com.project.credits.mapper.CreditMapper;
import com.project.credits.repository.CreditRepository;
import com.project.credits.request.CreateCreditRequest;
import com.project.credits.response.CreditResponse;
import com.project.credits.response.CreditResponseWithPaging;
import com.project.credits.service.impl.CreditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private UserService userService;

    @Mock
    private InstallmentService installmentService;

    @InjectMocks
    private CreditServiceImpl creditService;

    private User mockUser;
    private Credit mockCredit;
    private Page<Credit> mockCreditPage;
    private CreateCreditRequest mockCreateCreditRequest;

    @BeforeEach
    void setUp() {
        CreditMapper creditMapper = Mappers.getMapper(CreditMapper.class);
        creditService = new CreditServiceImpl(creditRepository, userService, installmentService, creditMapper);

        mockCreateCreditRequest = new CreateCreditRequest();
        mockCreateCreditRequest.setUserId(1L);
        mockCreateCreditRequest.setAmount(new BigDecimal("1000"));
        mockCreateCreditRequest.setInterestRate(10L);
        mockCreateCreditRequest.setInstallmentCount(3);

        mockUser = new User();
        mockUser.setId(1L);

        mockCredit = new Credit();
        mockCredit.setUser(mockUser);

        Credit mockCredit2 = new Credit();
        mockCredit2.setUser(mockUser);

        List<Credit> mockCredits = Arrays.asList(mockCredit, mockCredit2);

        mockCreditPage = new PageImpl<>(mockCredits);
    }

    @Test
    void createCredit_whenValidRequest_thenCreditIsCreatedSuccessfully() {
        when(userService.findUserById(mockCreateCreditRequest.getUserId())).thenReturn(mockUser);
        when(creditRepository.save(any(Credit.class))).thenReturn(mockCredit);

        CreditResponse result = creditService.createCredit(mockCreateCreditRequest);

        verify(userService, times(1)).findUserById(1L);
        verify(creditRepository, times(1)).save(any(Credit.class));

        assertNotNull(result);
    }

    @Test
    void findCreditsByUserId_whenValidRequest_thenReturnCredits() {
        when(creditRepository.findByUserId(1L)).thenReturn(List.of(mockCredit));

        List<CreditResponse> result = creditService.findCreditsByUserId(1L);

        verify(creditRepository, times(1)).findByUserId(1L);

        assertNotNull(result);
    }

    @Test
    void findCreditsByFilters_whenValidRequest_thenReturnCreditsWithPaging() {
        Pageable pageable = PageRequest.of(0, 10);

        when(creditRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(mockCreditPage);

        CreditResponseWithPaging result = creditService.findCreditsByFilters(1L, any(), any(), pageable);

        verify(creditRepository, times(1)).findAll(any(Specification.class), eq(pageable));

        assertNotNull(result);
    }

}
