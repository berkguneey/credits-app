package com.project.credits.service;

import com.project.credits.enums.CreditStatusEnum;
import com.project.credits.exception.EntityNotFoundException;
import com.project.credits.request.CreateCreditRequest;
import com.project.credits.response.CreditResponse;
import com.project.credits.response.CreditResponseWithPaging;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface CreditService {

    /**
     * Creates a new credit for the given user based on the provided request details.
     *
     * @param request The request object containing the user ID, credit amount, interest rate, and installment count.
     *                - userId: The ID of the user who is taking the credit. Cannot be null.
     *                - amount: The principal amount of the credit. Cannot be null.
     *                - interestRate: The interest rate applied to the credit. Cannot be null.
     *                - installmentCount: The number of installments in which the credit will be paid back. Must be greater than zero.
     * @return A {@link CreditResponse} containing the details of the created credit, including the installment plan.
     * @throws EntityNotFoundException If the specified user is not found.
     */
    CreditResponse createCredit(CreateCreditRequest request);

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
