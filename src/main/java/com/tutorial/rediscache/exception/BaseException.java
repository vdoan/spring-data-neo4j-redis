package com.tutorial.rediscache.exception;


public class BaseException extends RuntimeException {
    private final ResultCode code;

    public BaseException(ResultCode code) {
        super(code.toString());
        this.code = code;
    }

    public BaseException(String message, ResultCode code) {
        super(message);
        this.code = code;
    }

    public BaseException(String message, Throwable cause, ResultCode code) {
        super(message, cause);
        this.code = code;
    }

    public ResultCode getCode() {
        return code;
    }

}
