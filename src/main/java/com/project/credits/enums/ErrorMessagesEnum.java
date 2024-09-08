package com.project.credits.enums;

import lombok.Getter;

@Getter
public enum ErrorMessagesEnum {

    USER_NOT_FOUND(101L, "User not found."),
    INSTALLMENT_NOT_FOUND(102L, "Installment not found."),
    OVERPAYMENT_DETECTED(103L, "Payment amount exceeds the remaining balance."),
    INSTALLMENT_NOT_BELONG_TO_THE_USER(104L, "The installment you are trying to pay does not belong to the relevant user."),
    INSTALLMENT_ALREADY_PAID_BEFORE(105L, "The installment has already been paid before.");

    private final Long code;
    private final String message;

    ErrorMessagesEnum(Long code, String message) {
        this.code = code;
        this.message = message;
    }

}
