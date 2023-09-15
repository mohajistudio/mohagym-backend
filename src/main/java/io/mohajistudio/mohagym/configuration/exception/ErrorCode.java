package io.mohajistudio.mohagym.configuration.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH_001", " AUTHENTICATION_FAILED."),
    AUTHENTICATION_CONFLICT(HttpStatus.CONFLICT, "AUTH_002","AUTHENTICATION_CONFLICT."),
    NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "AUTH_004","NOT_AUTHORIZED."),
    NOT_FOUND_ADMIN(HttpStatus.NOT_FOUND, "AUTH_003","NOT_FOUND_ADMIN"),
    NOT_FOUND_BOOK(HttpStatus.NOT_FOUND, "BOOK_001","NOT_FOUND_BOOK"),
    BOOK_DUPLICATED(HttpStatus.FORBIDDEN, "BOOK_002","BOOK_DUPLICATED"),
    SELL_BOOK_FAILED(HttpStatus.BAD_REQUEST, "BOOK_003", "SELL_BOOK_FAILED"),
    NOT_FOUND_HISTORY(HttpStatus.NOT_FOUND, "HISTORY_001","NOT_FOUND_HISTORY"),
    BOOKSTORE_INFO_DUPLICATED(HttpStatus.FORBIDDEN, "BOOKSTORE_INFO_001","BOOKSTORE_INFO_DUPLICATED"),
    NOT_FOUND_BOOKSTORE_INFO(HttpStatus.NOT_FOUND, "BOOKSTORE_INFO_002","NOT_FOUND_BOOKSTORE_INFO");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
