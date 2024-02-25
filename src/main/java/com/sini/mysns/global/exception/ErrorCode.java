package com.sini.mysns.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재 하지 않는 멤버"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 이메일"),

    // POST
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재 하지 않는 포스트"),
    POST_CREATE_VALIDATION(HttpStatus.INTERNAL_SERVER_ERROR, "맞지 않는에러"),

    // COMMENT
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재 하지 않는 댓글"),
    COMMNET_MEMBER_NOT_EQAUL(HttpStatus.FORBIDDEN, "댓글 작성자가 아님"),


    // Image
    WRONG_IMAGE_FORMAT(HttpStatus.BAD_REQUEST,"잘못된 이미지 형식");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message)
    {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
