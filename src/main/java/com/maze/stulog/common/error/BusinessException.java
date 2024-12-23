package com.maze.stulog.common.error;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final int code;
    private final String message;

    public BusinessException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
