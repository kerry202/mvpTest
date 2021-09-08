package com.smartmqtt.network.http;

public class ServerError extends Exception {
    public int code;

    public ServerError(String message, int errorCode) {
        super(message);
        this.code = errorCode;
    }

    @Override
    public String toString() {
        return "code = " + code + "  " + super.toString();
    }
}
