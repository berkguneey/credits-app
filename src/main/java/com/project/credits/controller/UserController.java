package com.project.credits.controller;

import com.project.credits.enums.CreditStatusEnum;
import com.project.credits.response.CreditResponse;
import com.project.credits.response.CreditResponseWithPaging;
import com.project.credits.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Get all credits for a specific user",
            description = "Fetches all credit records associated with the given user ID.",
            parameters = {
                    @Parameter(name = "userId", description = "ID of the user whose credits are to be retrieved", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of credits",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(name = "CreditResponse List Example", value = ExampleModels.GET_USER_CREDITS_RESPONSE_EXAMPLE)
                            )
                    )
            }
    )
    @GetMapping("/{userId}/credits")
    public ResponseEntity<List<CreditResponse>> getUserCredits(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserCredits(userId));
    }

    @Operation(
            summary = "Filter and list user credits",
            description = "Retrieve a paginated list of credits for a specific user with optional filters for status and creation date range.",
            parameters = {
                    @Parameter(name = "userId", description = "ID of the user whose credits are to be retrieved", example = "1"),
                    @Parameter(name = "status", description = "The status of the credit. Can be 'ACTIVE' or 'PAID_OFF'.", example = "ACTIVE"),
                    @Parameter(name = "date", description = "The date for filtering credits. Format should be YYYY-MM-DD.", example = "2024-09-07"),
                    @Parameter(name = "page", description = "Page number for pagination. Defaults to 0.", example = "0"),
                    @Parameter(name = "size", description = "Page size for pagination. Defaults to 10.", example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of credits",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(name = "CreditResponseWithPaging Example", value = ExampleModels.GET_USER_CREDITS_WITH_FILTER_RESPONSE_EXAMPLE)
                            )
                    )
            }
    )
    @GetMapping("/{userId}/credits/filter")
    public ResponseEntity<CreditResponseWithPaging> getUserCreditsWithFilter(@PathVariable Long userId,
                                                                             @RequestParam(required = false) CreditStatusEnum status,
                                                                             @RequestParam(required = false) LocalDate date,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getUserCreditsWithFilter(userId, status, date, pageable));
    }

}
