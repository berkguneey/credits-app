package com.project.credits.exception;

import com.project.credits.enums.ErrorMessagesEnum;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final Long errorCode;

    public BusinessException(ErrorMessagesEnum errorMessagesEnum) {
        super(errorMessagesEnum.getMessage());
        this.errorCode = errorMessagesEnum.getCode();
    }

}
