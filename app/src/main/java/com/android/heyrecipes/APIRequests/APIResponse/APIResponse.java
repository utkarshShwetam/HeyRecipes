package com.android.heyrecipes.APIRequests.APIResponse;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Response;

public class APIResponse<T> {

    public APIResponse<T> create(Throwable error) {
        return new APIErrorResponse<>(!Objects.equals(error.getMessage(), "") ? error.getMessage() : "Network Error \n Check network connection");
    }

    public APIResponse<T> create(Response<T> response) {
        if (response.isSuccessful()) {
            T body = response.body();
            if (body == null || response.code() == 204) {
                return new APIEmptyResponse<>();
            } else {
                return new APISuccessResponse<>(body);
            }
        } else {
            String error = "";
            try {
                error = response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
                error = response.message();
            }
            return new APIErrorResponse<>(error);
        }

    }

    public static class APISuccessResponse<T> extends APIResponse<T> {

        private final T body;

        APISuccessResponse(T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }
    }

    public static class APIErrorResponse<T> extends APIResponse<T> {

        private final String errorMessage;

        APIErrorResponse(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public static class APIEmptyResponse<T> extends APIResponse<T> {

    }

}
