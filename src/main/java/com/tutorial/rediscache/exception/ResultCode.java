package com.tutorial.rediscache.exception;

import org.springframework.http.HttpStatus;

public enum ResultCode {

    SUCCESS(0, HttpStatus.OK),

    UNKNOWN(Integer.MAX_VALUE, HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PARAM(1, HttpStatus.BAD_REQUEST),
    FEED_NOT_FOUND(2, HttpStatus.NOT_FOUND),
    INVALID_RESOURCE(3, HttpStatus.BAD_REQUEST),
    INVALID_VIDEO_RESOURCE(4, HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(5, HttpStatus.BAD_REQUEST),
    PARTY_NOT_FOUND(6, HttpStatus.BAD_REQUEST),
    COMMENT_NOT_FOUND(7, HttpStatus.BAD_REQUEST),
    RESOURCE_ACCESS_DENY(8, HttpStatus.FORBIDDEN),
    RESOURCE_NOT_AVAILABLE(9, HttpStatus.BAD_REQUEST),
    DATA_RESOURCE_NOT_FOUND(10, HttpStatus.BAD_REQUEST),
    SAVE_NOT_FOUND(11, HttpStatus.BAD_REQUEST),
    FILE_STORAGE_EXCEPTION(12, HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(13, HttpStatus.UNAUTHORIZED),
    BAD_REQUEST(14, HttpStatus.BAD_REQUEST),
    NOT_REACHABLE(15, HttpStatus.NOT_FOUND),
    SUBSCRIPTION_NOT_FOUND(2, HttpStatus.NOT_FOUND),
    EXISTS(16, HttpStatus.CONFLICT),
    ;

    // must start with 1
    private final int id;
    private final HttpStatus status;

    ResultCode(int id, HttpStatus status) {
        this.id = id;
        this.status = status;
    }

    public static ResultCode of(int id) {
        for (ResultCode e : ResultCode.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return UNKNOWN;
    }


    public int getId() {
        return id;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
