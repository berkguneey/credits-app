package com.project.credits.service;

import com.project.credits.entity.Credit;
import com.project.credits.entity.User;
import com.project.credits.enums.CreditStatusEnum;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private Credit mockCredit1;
    private CreateCreditRequest mockCreateCreditRequest;
    private List<Credit> mockCredits;
    private Page<Credit> mockCreditPage;
    private CreditResponseWithPaging expectedResponse;

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

        mockCredit1 = new Credit();
        mockCredit1.setUser(mockUser);

        Credit mockCredit2 = new Credit();
        mockCredit2.setUser(mockUser);

        CreditResponse mockCreditResponse1 = new CreditResponse();
        mockCreditResponse1.setInstallments(new ArrayList<>());

        CreditResponse mockCreditResponse2 = new CreditResponse();
        mockCreditResponse2.setInstallments(new ArrayList<>());

        mockCredits = Arrays.asList(mockCredit1, mockCredit2);

        mockCreditPage = new PageImpl<>(mockCredits);

        List<CreditResponse> mockResponses = Arrays.asList(mockCreditResponse1, mockCreditResponse2);
        expectedResponse = new CreditResponseWithPaging();
        expectedResponse.setCredits(mockResponses);
        expectedResponse.setTotalElements(mockCreditPage.getTotalElements());
        expectedResponse.setTotalPages(mockCreditPage.getTotalPages());
    }

    @Test
    void createCredit_whenValidRequest_thenCreditIsCreatedSuccessfully() {
        when(userService.findUserById(mockCreateCreditRequest.getUserId())).thenReturn(mockUser);
        when(creditRepository.save(any(Credit.class))).thenReturn(mockCredit1);

        CreditResponse result = creditService.createCredit(mockCreateCreditRequest);

        verify(userService, times(1)).findUserById(1L);
        verify(creditRepository, times(1)).save(any(Credit.class));

        assertNotNull(result);
    }

    @Test
    void getUserCredits_whenValidRequest_thenReturnCreditResponses() {
        when(userService.findUserById(mockUser.getId())).thenReturn(mockUser);
        when(creditRepository.findByUser(mockUser)).thenReturn(mockCredits);

        List<CreditResponse> result = creditService.getUserCredits(mockUser.getId());

        verify(userService, times(1)).findUserById(mockUser.getId());
        verify(creditRepository, times(1)).findByUser(mockUser);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getUserCreditsWithFilter_whenUserExistsAndFilterApplied_thenReturnCreditResponseWithPaging() {
        Long userId = mockUser.getId();
        CreditStatusEnum status = CreditStatusEnum.ACTIVE;
        LocalDate date = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 10);

        when(userService.findUserById(userId)).thenReturn(mockUser);
        when(creditRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(mockCreditPage);

        CreditResponseWithPaging result = creditService.getUserCreditsWithFilter(userId, status, date, pageable);

        verify(userService, times(1)).findUserById(userId);
        verify(creditRepository, times(1)).findAll(any(Specification.class), eq(pageable));

        assertNotNull(result);
        assertEquals(expectedResponse.getTotalElements(), result.getTotalElements());
        assertEquals(expectedResponse.getTotalPages(), result.getTotalPages());
    }

}
