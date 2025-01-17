package com.tuyendv.web.backend.api.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiStatus {
    // 2xx
    // 200 Success
    SUCCESS(HttpStatus.OK, "200001", "Success"),

    // 201 Created
    CREATED(HttpStatus.CREATED, "201001", "Created"),

    // 202 Accepted
    ACCEPTED(HttpStatus.ACCEPTED, "202001", "Accepted"),

    // 4xx
    // 400 Bad request (Data validator)
    BAD_REQUEST_VALID(HttpStatus.BAD_REQUEST, "400001", "Bad request"),

    // 400 Bad request (Business Exception)
    BAD_REQUEST_BUSINESS(HttpStatus.BAD_REQUEST, "400002", "Business"),

    // 400 Bad request (Empty file)
    BAD_REQUEST_FILE_EMPTY(HttpStatus.BAD_REQUEST, "400003", "Empty file"),

    // 400 Bad request (Invalid file extension.)
    BAD_REQUEST_FILE_EXTENSIONS(HttpStatus.BAD_REQUEST, "400004", "Invalid file extension."),

    // 400 Bad request (Could not read the file!)
    BAD_REQUEST_FILE_NOT_READ(HttpStatus.BAD_REQUEST, "400005", "Could not read the file!"),

    // 400 Bad request (File does not exist)
    BAD_REQUEST_FILE_NOT_EXIST(HttpStatus.BAD_REQUEST, "400005", "File does not exist"),

    // 400 Bad request (Token format)
    INCORRECT_FORMAT(HttpStatus.BAD_REQUEST, "400006", "Token is not correct format"),

    // 400 Bad request (Token verify)
    BAD_REQUEST_VERIFY_TOKEN(HttpStatus.BAD_REQUEST, "400006", "Cannot verifier token"),

    // 400 Bad request (Token refresh)
    BAD_REQUEST_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "400006", "Cannot refresh token"),

    //401 Unauthenticated (JWT)
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "401001", "Unauthenticated Token"),

    // 401 Unauthorized Error
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "401002", "Unauthorized Error"),

    // 401 Incorrect username or password
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "401003", "Incorrect username or password"),

    // 403 Access denied
    FORBIDDEN(HttpStatus.FORBIDDEN, "403001", "Access denied"),

    // 404 Not found
    NOT_FOUND(HttpStatus.NOT_FOUND, "404001", "Not found"),

    // 404 User Exist
    USER_EXISTED(HttpStatus.NOT_FOUND, "404002","User existed"),

    // 409 Already exist
    CONFLICT(HttpStatus.CONFLICT, "409001", "Conflict"),

    // 413 Payload Too Large
    PAYLOAD_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "413001", "Payload Too Large"),

    // Unprocessable entity
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, "422001", "Unprocessable entity"),

    // 5xx
    // Internal server error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500001", "Internal server error");

    private final HttpStatus httpStatus;

    private final String code;

    private final String message;


    ApiStatus(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
