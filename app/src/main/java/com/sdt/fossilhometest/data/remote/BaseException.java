package com.sdt.fossilhometest.data.remote;

import androidx.annotation.Nullable;

import retrofit2.Response;

public class BaseException extends RuntimeException {

    private static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected Error!";

    private ErrorType errorType;
    private ServerErrorResponse serverErrorResponse;

    private Response<?> response;
    private int httpCode;

    private Throwable cause;

    public BaseException(Builder builder) {
        this.cause = builder.cause;
        this.response = builder.response;
        this.httpCode = builder.httpCode;
        this.errorType = builder.errorType;
        this.serverErrorResponse = builder.serverErrorResponse;
    }

    @Nullable
    @Override
    public String getMessage() {
        switch (errorType) {
            case HTTP:
                return response != null ? response.message() : UNEXPECTED_ERROR_MESSAGE;
            case SERVER:
                if (serverErrorResponse != null
                    && serverErrorResponse.getErrors() != null
                    && serverErrorResponse.getErrors().size() > 1) {
                    return serverErrorResponse.getErrors().get(0);
                }
                return UNEXPECTED_ERROR_MESSAGE;
            default:
                return cause.getMessage();
        }
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public ServerErrorResponse getServerErrorResponse() {
        return serverErrorResponse;
    }

    public Response<?> getResponse() {
        return response;
    }

    public int getHttpCode() {
        return httpCode;
    }

    @Nullable
    @Override
    public Throwable getCause() {
        return cause;
    }

    public static class Builder {

        private ErrorType errorType;
        private ServerErrorResponse serverErrorResponse;

        private Response<?> response;
        private int httpCode;

        private Throwable cause;

        public Builder() {
        }

        public Builder errorType(ErrorType errorType) {
            this.errorType = errorType;
            return this;
        }

        public Builder serverErrorResponse(ServerErrorResponse serverErrorResponse) {
            this.serverErrorResponse = serverErrorResponse;
            return this;
        }

        public Builder response(Response<?> response) {
            this.response = response;
            return this;
        }

        public Builder httpCode(int httpCode) {
            this.httpCode = httpCode;
            return this;
        }

        public Builder cause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public BaseException build() {
            return new BaseException(this);
        }
    }

}
