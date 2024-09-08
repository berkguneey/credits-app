package com.project.credits.controller;

import com.project.credits.request.CreateCreditRequest;
import com.project.credits.request.PayInstallmentRequest;
import com.project.credits.response.CreditResponse;
import com.project.credits.response.CreditResponseWithPaging;
import io.swagger.v3.oas.annotations.media.Schema;

public class ExampleModels {

    @Schema(name = "CreateCreditRequest", implementation = CreateCreditRequest.class, description = "Example of Create Credit Request")
    public static final String CREATE_CREDIT_REQUEST_EXAMPLE = "{\n" +
            "    \"userId\": 1,\n" +
            "    \"amount\": 100,\n" +
            "    \"interestRate\": 10,\n" +
            "    \"installmentCount\": 3\n" +
            "}";

    @Schema(name = "CreditResponse", implementation = CreditResponse.class, description = "Example of Create Credit Response")
    public static final String CREATE_CREDIT_RESPONSE_EXAMPLE = "{\n" +
            "    \"id\": 1,\n" +
            "    \"principalAmount\": 100,\n" +
            "    \"installments\": [\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"amount\": 33.33,\n" +
            "            \"dueDate\": \"2024-10-07\",\n" +
            "            \"overdueDays\": 0,\n" +
            "            \"overdueFeeAmount\": 0,\n" +
            "            \"status\": \"PENDING\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"amount\": 33.33,\n" +
            "            \"dueDate\": \"2024-11-06\",\n" +
            "            \"overdueDays\": 0,\n" +
            "            \"overdueFeeAmount\": 0,\n" +
            "            \"status\": \"PENDING\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 3,\n" +
            "            \"amount\": 66.67,\n" +
            "            \"dueDate\": \"2024-12-06\",\n" +
            "            \"overdueDays\": 0,\n" +
            "            \"overdueFeeAmount\": 0,\n" +
            "            \"status\": \"PENDING\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Schema(name = "CreditResponse", implementation = CreditResponse.class, type = "array", description = "Example of Get User Credit Response")
    public static final String GET_USER_CREDITS_RESPONSE_EXAMPLE = "[\n" +
            "    {\n" +
            "        \"id\": 1,\n" +
            "        \"principalAmount\": 100.00,\n" +
            "        \"installments\": [\n" +
            "            {\n" +
            "                \"id\": 1,\n" +
            "                \"amount\": 33.33,\n" +
            "                \"dueDate\": \"2024-10-07\",\n" +
            "                \"overdueDays\": 0,\n" +
            "                \"overdueFeeAmount\": 0.00,\n" +
            "                \"status\": \"PENDING\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 2,\n" +
            "                \"amount\": 33.33,\n" +
            "                \"dueDate\": \"2024-11-06\",\n" +
            "                \"overdueDays\": 0,\n" +
            "                \"overdueFeeAmount\": 0.00,\n" +
            "                \"status\": \"PENDING\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 3,\n" +
            "                \"amount\": 33.34,\n" +
            "                \"dueDate\": \"2024-12-06\",\n" +
            "                \"overdueDays\": 0,\n" +
            "                \"overdueFeeAmount\": 0.00,\n" +
            "                \"status\": \"PENDING\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 2,\n" +
            "        \"principalAmount\": 150.00,\n" +
            "        \"installments\": [\n" +
            "            {\n" +
            "                \"id\": 4,\n" +
            "                \"amount\": 37.50,\n" +
            "                \"dueDate\": \"2024-10-07\",\n" +
            "                \"overdueDays\": 0,\n" +
            "                \"overdueFeeAmount\": 0.00,\n" +
            "                \"status\": \"PENDING\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 5,\n" +
            "                \"amount\": 37.50,\n" +
            "                \"dueDate\": \"2024-11-06\",\n" +
            "                \"overdueDays\": 0,\n" +
            "                \"overdueFeeAmount\": 0.00,\n" +
            "                \"status\": \"PENDING\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 6,\n" +
            "                \"amount\": 37.50,\n" +
            "                \"dueDate\": \"2024-12-06\",\n" +
            "                \"overdueDays\": 0,\n" +
            "                \"overdueFeeAmount\": 0.00,\n" +
            "                \"status\": \"PENDING\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 7,\n" +
            "                \"amount\": 37.50,\n" +
            "                \"dueDate\": \"2025-01-06\",\n" +
            "                \"overdueDays\": 0,\n" +
            "                \"overdueFeeAmount\": 0.00,\n" +
            "                \"status\": \"PENDING\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "]";

    @Schema(name = "CreditResponse", implementation = CreditResponseWithPaging.class, description = "Example of Get User Credit With Filter Response")
    public static final String GET_USER_CREDITS_WITH_FILTER_RESPONSE_EXAMPLE = "{\n" +
            "    \"credits\": [\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"principalAmount\": 250.00,\n" +
            "            \"installments\": [\n" +
            "                {\n" +
            "                    \"id\": 1,\n" +
            "                    \"amount\": 62.50,\n" +
            "                    \"dueDate\": \"2024-10-07\",\n" +
            "                    \"overdueDays\": 0,\n" +
            "                    \"overdueFeeAmount\": 0.00,\n" +
            "                    \"status\": \"PENDING\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 2,\n" +
            "                    \"amount\": 62.50,\n" +
            "                    \"dueDate\": \"2024-11-06\",\n" +
            "                    \"overdueDays\": 0,\n" +
            "                    \"overdueFeeAmount\": 0.00,\n" +
            "                    \"status\": \"PENDING\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 3,\n" +
            "                    \"amount\": 62.50,\n" +
            "                    \"dueDate\": \"2024-12-06\",\n" +
            "                    \"overdueDays\": 0,\n" +
            "                    \"overdueFeeAmount\": 0.00,\n" +
            "                    \"status\": \"PENDING\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 4,\n" +
            "                    \"amount\": 62.50,\n" +
            "                    \"dueDate\": \"2025-01-06\",\n" +
            "                    \"overdueDays\": 0,\n" +
            "                    \"overdueFeeAmount\": 0.00,\n" +
            "                    \"status\": \"PENDING\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"principalAmount\": 150.00,\n" +
            "            \"installments\": [\n" +
            "                {\n" +
            "                    \"id\": 5,\n" +
            "                    \"amount\": 37.50,\n" +
            "                    \"dueDate\": \"2024-10-07\",\n" +
            "                    \"overdueDays\": 0,\n" +
            "                    \"overdueFeeAmount\": 0.00,\n" +
            "                    \"status\": \"PENDING\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 6,\n" +
            "                    \"amount\": 37.50,\n" +
            "                    \"dueDate\": \"2024-11-06\",\n" +
            "                    \"overdueDays\": 0,\n" +
            "                    \"overdueFeeAmount\": 0.00,\n" +
            "                    \"status\": \"PENDING\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 7,\n" +
            "                    \"amount\": 37.50,\n" +
            "                    \"dueDate\": \"2024-12-06\",\n" +
            "                    \"overdueDays\": 0,\n" +
            "                    \"overdueFeeAmount\": 0.00,\n" +
            "                    \"status\": \"PENDING\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 8,\n" +
            "                    \"amount\": 37.50,\n" +
            "                    \"dueDate\": \"2025-01-06\",\n" +
            "                    \"overdueDays\": 0,\n" +
            "                    \"overdueFeeAmount\": 0.00,\n" +
            "                    \"status\": \"PENDING\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ],\n" +
            "    \"totalElements\": 2,\n" +
            "    \"totalPages\": 1\n" +
            "}";

    @Schema(name = "PayInstallmentRequest", implementation = PayInstallmentRequest.class, description = "Example of Pay Installment Request")
    public static final String PAY_INSTALLMENT_REQUEST_EXAMPLE = "{\n" +
            "    \"userId\": 1,\n" +
            "    \"installmentId\": 1,\n" +
            "    \"amount\": 83.33\n" +
            "}";

}
