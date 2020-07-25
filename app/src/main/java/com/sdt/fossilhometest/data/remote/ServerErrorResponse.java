package com.sdt.fossilhometest.data.remote;

import java.util.List;

public class ServerErrorResponse {

    private String message;
    private List<String> errors;

    public ServerErrorResponse() {
    }

    public ServerErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
