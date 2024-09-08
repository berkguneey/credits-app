package com.project.credits.service.impl;

import com.project.credits.entity.User;
import com.project.credits.enums.ErrorMessagesEnum;
import com.project.credits.exception.EntityNotFoundException;
import com.project.credits.repository.UserRepository;
import com.project.credits.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessagesEnum.USER_NOT_FOUND));
    }

}
