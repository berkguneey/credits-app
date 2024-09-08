package com.project.credits.controller;

import com.project.credits.request.PayInstallmentRequest;
import com.project.credits.service.InstallmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/installments")
@RequiredArgsConstructor
public class InstallmentController {

    private final InstallmentService installmentService;

    @Operation(
            summary = "Pay an installment",
            description = "Process the payment for an installment with the specified details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Installment payment request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "PayInstallmentRequest Example", value = ExampleModels.PAY_INSTALLMENT_REQUEST_EXAMPLE)
                    )
            )
    )
    @PostMapping("/pay")
    public ResponseEntity<Void> payInstallment(@RequestBody @Valid PayInstallmentRequest request) {
        installmentService.payInstallment(request.getUserId(), request.getInstallmentId(), request.getAmount());
        return ResponseEntity.ok().build();
    }

}
