package com.ash.teacheron.retrofit.model;

public class ErrorData {
    private String message;
    private Integer errorCode;

    public ErrorData(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
