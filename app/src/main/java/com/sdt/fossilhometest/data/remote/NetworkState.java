package com.sdt.fossilhometest.data.remote;

public final class NetworkState {

    public static NetworkState LOADED = new NetworkState(Status.SUCCESS);
    public static NetworkState LOADING = new NetworkState(Status.RUNNING);

    public static NetworkState error(String message) {
        return new NetworkState(Status.FAILED, message);
    }

    private Status status;
    private String message;

    public NetworkState(Status status) {
        this.status = status;
    }

    public NetworkState(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}

