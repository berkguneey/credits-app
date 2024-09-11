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
     * Retrieves a list of credits associated with the specified user ID.
     *
     * @param userId The ID of the user whose credits are to be retrieved. Cannot be null.
     * @return A list of {@link CreditResponse} objects containing the details of the user's credits.
     * @throws EntityNotFoundException If no credits are found for the specified user ID.
     */
    List<CreditResponse> findCreditsByUserId(Long userId);

    /**
     * Retrieves a paginated list of credits based on the specified filters.
     *
     * @param userId   The ID of the user whose credits are to be retrieved. Can be null to retrieve credits for all users.
     * @param status   The status of the credits to be retrieved. Can be null to retrieve credits of all statuses.
     * @param date     The specific date to filter credits. Can be null to retrieve credits for all dates.
     * @param pageable The pagination information.
     * @return A {@link CreditResponseWithPaging} object containing the paginated list of credits and pagination details.
     */
    CreditResponseWithPaging findCreditsByFilters(Long userId, CreditStatusEnum status, LocalDate date, Pageable pageable);

}
