package com.sini.mysns.global.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String errorMessage, String code, int status)
{
    public static ErrorResponse badRequest(String message)
    {
        return new ErrorResponse(
                message,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    public static ErrorResponse error(String message)
    {
        return new ErrorResponse(
                message,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }

    public static ErrorResponse error(ErrorCode errorCode)
    {
        return new ErrorResponse(
                errorCode.getMessage(),
                errorCode.toString(),
                errorCode.getHttpStatus().value()
        );
    }
}
