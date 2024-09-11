package com.project.credits.service.impl;

import com.project.credits.entity.User;
import com.project.credits.enums.CreditStatusEnum;
import com.project.credits.enums.ErrorMessagesEnum;
import com.project.credits.exception.EntityNotFoundException;
import com.project.credits.repository.UserRepository;
import com.project.credits.response.CreditResponse;
import com.project.credits.response.CreditResponseWithPaging;
import com.project.credits.service.CreditService;
import com.project.credits.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CreditService creditService;

    public UserServiceImpl(UserRepository userRepository, CreditService creditService) {
        this.userRepository = userRepository;
        this.creditService = creditService;
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessagesEnum.USER_NOT_FOUND));
    }

    @Override
    public List<CreditResponse> getUserCredits(Long userId) {
        User user = this.findUserById(userId);
        return creditService.findCreditsByUserId(user.getId());
    }

    @Override
    public CreditResponseWithPaging getUserCreditsWithFilter(Long userId, CreditStatusEnum status, LocalDate date, Pageable pageable) {
        User user = this.findUserById(userId);
        return creditService.findCreditsByFilters(user.getId(), status, date, pageable);
    }

}
