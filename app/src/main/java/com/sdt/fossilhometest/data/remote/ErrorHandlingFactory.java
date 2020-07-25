package com.sdt.fossilhometest.data.remote;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Response;

public final class ErrorHandlingFactory {

    private ErrorHandlingFactory() {}

    public static BaseException convertToBaseException(Throwable throwable) {
        if (throwable instanceof BaseException) {
            return (BaseException) throwable;
        } else if (throwable instanceof IOException) {
            return toNetworkError(throwable);
        } else if (throwable instanceof HttpException) {
            HttpException httpException = ((HttpException) throwable);
            Response<?> response = httpException.response();
            int httpCode = httpException.code();

            if (response != null && response.errorBody() == null) {
                return toHttpError(response, httpCode);
            }

            String serverErrorResponseBody = "";
            try {
                serverErrorResponseBody = response != null ? response.errorBody().string(): "";
            } catch (Exception e) {
                e.printStackTrace();
            }

            ServerErrorResponse serverErrorResponse = null;
            try {
                serverErrorResponse =
                    new Gson().fromJson(serverErrorResponseBody, ServerErrorResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (serverErrorResponse != null) {
                return toServerError(serverErrorResponse, response, httpCode);
            } else {
                return toHttpError(response, httpCode);
            }
        } else {
            return toUnexpectedError(throwable);
        }
    }

    public static BaseException toHttpError(Response<?> response, int httpCode) {
        return new BaseException.Builder()
            .errorType(ErrorType.HTTP)
            .response(response)
            .httpCode(httpCode)
            .build();
    }

    public static BaseException toNetworkError(Throwable cause) {
        return new BaseException.Builder()
            .errorType(ErrorType.NETWORK)
            .cause(cause)
            .build();
    }

    public static BaseException toServerError(ServerErrorResponse serverErrorResponse,
                                              Response<?> response,
                                              int httpCode) {
        return new BaseException.Builder()
            .errorType(ErrorType.SERVER)
            .serverErrorResponse(serverErrorResponse)
            .response(response)
            .httpCode(httpCode)
            .build();
    }

    public static BaseException toUnexpectedError(Throwable cause) {
        return new BaseException.Builder()
            .errorType(ErrorType.UNEXPECTED)
            .cause(cause)
            .build();
    }
}
