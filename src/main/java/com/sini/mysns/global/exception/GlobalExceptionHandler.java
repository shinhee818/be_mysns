package com.sini.mysns.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception)
    {
        log.error("MethodArgumentNotValidException handle");
        String message = exception.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ErrorResponse.badRequest(message);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception)
    {
        log.error("HttpMessageNotReadableException handle : {}",exception.getMessage());
        String message = "request parsing 에러";
        return ErrorResponse.badRequest(message);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException apiException)
    {
        ErrorCode errorCode = apiException.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.error(errorCode));
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception exception)
    {
        log.error("Error Code : {}", exception.getMessage());
        log.error("Error Class:{}",exception.getClass());
        return ErrorResponse.error(exception.getMessage());
    }
}
