package com.vintiduo.logfrontend.core.models;

/**
 * Created by Kostas on 2016.11.01.
 */
public class LogViewModel {
    private long position;
    private String message;

    public LogViewModel(long position, String message) {
        this.position = position;
        this.message = message;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
