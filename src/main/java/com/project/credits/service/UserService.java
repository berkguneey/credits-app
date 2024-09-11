package com.project.credits.service;

import com.project.credits.entity.User;
import com.project.credits.enums.CreditStatusEnum;
import com.project.credits.exception.EntityNotFoundException;
import com.project.credits.response.CreditResponse;
import com.project.credits.response.CreditResponseWithPaging;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the User entity if found
     * @throws EntityNotFoundException if the user with the specified ID is not found
     */
    User findUserById(Long userId);

    /**
     * Retrieves a list of all credits associated with a specific user.
     *
     * @param userId The ID of the user whose credits are to be retrieved. Cannot be null.
     * @return A list of {@link CreditResponse} objects containing the details of all credits for the specified user.
     * @throws EntityNotFoundException If the user with the specified ID is not found.
     */
    List<CreditResponse> getUserCredits(Long userId);

    /**
     * Retrieves a paginated list of credits for a specific user with optional filters for credit status and creation date.
     *
     * @param userId   The ID of the user whose credits are to be retrieved. Cannot be null.
     * @param status   An optional filter for the status of the credits (e.g., ACTIVE, CLOSED). If null, no filter is applied.
     * @param date     An optional filter for the creation date of the credits. If null, no filter is applied.
     * @param pageable Pagination details including the page number and page size.
     * @return A {@link CreditResponseWithPaging} containing the filtered list of credits along with pagination information.
     * @throws EntityNotFoundException If the user with the specified ID is not found.
     */
    CreditResponseWithPaging getUserCreditsWithFilter(Long userId, CreditStatusEnum status, LocalDate date, Pageable pageable);

}
