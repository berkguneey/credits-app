package com.project.credits.controller;

import com.project.credits.request.CreateCreditRequest;
import com.project.credits.response.CreditResponse;
import com.project.credits.service.CreditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credits")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @Operation(
            summary = "Create a new credit",
            description = "Creates a new credit with specified details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credit creation request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "CreateCreditRequest Example", value = ExampleModels.CREATE_CREDIT_REQUEST_EXAMPLE)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Credit created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(name = "CreditResponse Example", value = ExampleModels.CREATE_CREDIT_RESPONSE_EXAMPLE)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<CreditResponse> createCredit(@Validated @RequestBody CreateCreditRequest request) {
        return new ResponseEntity<>(creditService.createCredit(request), HttpStatus.CREATED);
    }

}
