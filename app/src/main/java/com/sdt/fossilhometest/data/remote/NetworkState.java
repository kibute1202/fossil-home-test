package com.sdt.fossilhometest.data.remote;

public final class NetworkState {

    public static NetworkState LOADED = new NetworkState(Status.SUCCESS);
    public static NetworkState LOADING = new NetworkState(Status.RUNNING);

    public static NetworkState error(Throwable cause) {
        return new NetworkState(Status.FAILED, cause);
    }

    private Status status;
    private Throwable cause;

    public NetworkState(Status status) {
        this.status = status;
    }

    public NetworkState(Status status, Throwable cause) {
        this.status = status;
        this.cause = cause;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}

