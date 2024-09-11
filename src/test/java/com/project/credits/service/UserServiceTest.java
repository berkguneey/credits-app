package com.project.credits.service;

import com.project.credits.entity.User;
import com.project.credits.enums.CreditStatusEnum;
import com.project.credits.enums.ErrorMessagesEnum;
import com.project.credits.exception.EntityNotFoundException;
import com.project.credits.repository.UserRepository;
import com.project.credits.response.CreditResponse;
import com.project.credits.response.CreditResponseWithPaging;
import com.project.credits.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CreditService creditService;

    @InjectMocks
    private UserServiceImpl userService;

    private Optional<User> mockUserOpt;
    private List<CreditResponse> mockCreditResponses;
    private CreditResponseWithPaging mockCreditResponseWithPaging;

    @BeforeEach
    void setUp() {
        User mockUser = new User();
        mockUser.setId(1L);

        mockUserOpt = Optional.of(mockUser);

        CreditResponse mockCreditResponse1 = new CreditResponse();
        mockCreditResponse1.setInstallments(new ArrayList<>());

        CreditResponse mockCreditResponse2 = new CreditResponse();
        mockCreditResponse2.setInstallments(new ArrayList<>());

        mockCreditResponses = Arrays.asList(mockCreditResponse1, mockCreditResponse2);

        mockCreditResponseWithPaging = new CreditResponseWithPaging();
        mockCreditResponseWithPaging.setCredits(mockCreditResponses);
        mockCreditResponseWithPaging.setTotalElements(2);
        mockCreditResponseWithPaging.setTotalPages(1);
    }

    @Test
    void findUserById_whenValidRequest_thenReturnUserSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(mockUserOpt);

        User result = userService.findUserById(1L);

        verify(userRepository, times(1)).findById(1L);

        assertNotNull(result);
    }

    @Test
    void createCredit_whenUserNotFound_thenThrowEntityNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.findUserById(1L));

        verify(userRepository, times(1)).findById(1L);

        assertEquals(ErrorMessagesEnum.USER_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void getUserCredits_whenValidRequest_thenReturnCreditResponses() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(mockUserOpt);
        when(creditService.findCreditsByUserId(userId)).thenReturn(mockCreditResponses);

        List<CreditResponse> result = userService.getUserCredits(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(creditService, times(1)).findCreditsByUserId(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getUserCreditsWithFilter_whenUserExistsAndFilterApplied_thenReturnCreditResponseWithPaging() {
        Long userId = 1L;
        CreditStatusEnum status = CreditStatusEnum.ACTIVE;
        LocalDate date = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findById(userId)).thenReturn(mockUserOpt);
        when(creditService.findCreditsByFilters(any(), any(), any(), any())).thenReturn(mockCreditResponseWithPaging);

        CreditResponseWithPaging result = userService.getUserCreditsWithFilter(userId, status, date, pageable);

        verify(userRepository, times(1)).findById(userId);
        verify(creditService, times(1)).findCreditsByFilters(any(), any(), any(), any());

        assertNotNull(result);
        assertEquals(mockCreditResponseWithPaging.getTotalElements(), result.getTotalElements());
        assertEquals(mockCreditResponseWithPaging.getTotalPages(), result.getTotalPages());
    }

}
