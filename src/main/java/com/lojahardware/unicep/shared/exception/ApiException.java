package com.lojahardware.unicep.shared.exception;

public class ApiException extends RuntimeException {

    private final int statusCode;

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ApiException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static ApiException badRequest(String message) {
        return new ApiException(message, 400);
    }

    public static ApiException unauthorized(String message) {
        return new ApiException(message, 401);
    }

    public static ApiException forbidden(String message) {
        return new ApiException(message, 403);
    }

    public static ApiException notFound(String message) {
        return new ApiException(message, 404);
    }

    public static ApiException conflict(String message) {
        return new ApiException(message, 409);
    }

    public static ApiException internalServerError(String message) {
        return new ApiException(message, 500);
    }
}
