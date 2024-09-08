package com.project.credits.service;

import com.project.credits.entity.User;
import com.project.credits.enums.ErrorMessagesEnum;
import com.project.credits.exception.EntityNotFoundException;
import com.project.credits.repository.UserRepository;
import com.project.credits.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
    }

    @Test
    void findUserById_whenValidRequest_thenReturnUserSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

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

}
