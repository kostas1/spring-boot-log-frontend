package com.vintiduo.logfrontend.core.models;

import java.util.List;

/**
 * Created by Kostas on 2016.10.28.
 */
public class LogsViewModel {

    private Long seekFrom;
    private Long seekTo;
    private List<LogViewModel> logs;

    public LogsViewModel(Long seekFrom, Long seekTo, List<LogViewModel> logs) {
        this.seekFrom = seekFrom;
        this.seekTo = seekTo;
        this.logs = logs;
    }

    public Long getSeekFrom() {
        return seekFrom;
    }

    public void setSeekFrom(Long seekFrom) {
        this.seekFrom = seekFrom;
    }

    public Long getSeekTo() {
        return seekTo;
    }

    public void setSeekTo(Long seekTo) {
        this.seekTo = seekTo;
    }

    public List<LogViewModel> getLogs() {
        return logs;
    }

    public void setLogs(List<LogViewModel> logs) {
        this.logs = logs;
    }
}
