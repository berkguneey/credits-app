package com.project.credits.exception;

import com.project.credits.enums.ErrorMessagesEnum;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final Long errorCode;

    public EntityNotFoundException(ErrorMessagesEnum errorMessagesEnum) {
        super(errorMessagesEnum.getMessage());
        this.errorCode = errorMessagesEnum.getCode();
    }
    
}
