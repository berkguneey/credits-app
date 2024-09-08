package com.project.credits.service;

import com.project.credits.entity.User;
import com.project.credits.exception.EntityNotFoundException;

public interface UserService {

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the User entity if found
     * @throws EntityNotFoundException if the user with the specified ID is not found
     */
    User findUserById(Long userId);

}
