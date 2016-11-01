package com.vintiduo.logfrontend.core.services;

import com.vintiduo.logfrontend.core.models.LogViewModel;
import com.vintiduo.logfrontend.core.models.LogsViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostas on 2016.10.28.
 */
@Component
public class LogProvider {

    Logger logger = LoggerFactory.getLogger(LogProvider.class);

    public LogsViewModel logLines(Long seekFrom, Long seekTo) throws IOException {
        File f = new File("new.log");
        RandomAccessFile file = new RandomAccessFile(f, "r");
        LogsViewModel lines;
        seekFrom = assureBounds(file.length(), seekFrom);
        seekTo = assureBounds(file.length(), seekTo);
        if (seekTo == null) {
            if (seekFrom == null) {
                lines = readLast2(file, f.length(), 5);
            } else {
                lines = readFirst(file, seekFrom, 5);
            }
        } else {
            if (seekFrom == null) {
                lines = readLast2(file, seekTo, 5);
            } else {
                lines = readInterval(file, seekFrom, seekTo);
            }
        }
        return lines;
    }

    private Long assureBounds(long length, Long value) {
        if (value == null) {
            return null;
        }
        if (value < 0) {
            return 0L;
        }
        if (value > length) {
            return length;
        }

        return value;
    }

    private LogsViewModel readInterval(RandomAccessFile file, Long seekFrom, Long seekTo) throws IOException {
        List<LogViewModel> logs = new ArrayList<LogViewModel>();
        file.seek(seekFrom);
        if (file.getFilePointer() > 0) {
            file.readLine(); // skip incomplete line
        }
        if (seekTo >= seekFrom) { // todo assure it includes first if position matches real line start position, it works with end
            while (file.getFilePointer() <= seekTo) {
                logs.add(new LogViewModel(file.getFilePointer(), file.readLine()));
            }
        }
        return new LogsViewModel(seekFrom, file.getFilePointer(), logs);
    }

    private LogsViewModel readFirst(RandomAccessFile file, Long seekFrom, int count) throws IOException {
        List<LogViewModel> logs = new ArrayList<LogViewModel>();
        file.seek(seekFrom);
        if (file.getFilePointer() > 0) { // todo assure it includes first if position matches real line start position, it works with end
            file.readLine(); // skip incomplete line
        }
        int found = 0;
        while (found < count && file.getFilePointer() < file.length()) {
            logs.add(new LogViewModel(file.getFilePointer(), file.readLine()));
            found++;
        }
        return new LogsViewModel(seekFrom, file.getFilePointer(), logs);
    }

    private long currentLastOffset = 10000;

    private LogsViewModel readLast2(RandomAccessFile file, long seekTo, int breakCount) throws IOException {
        List<LogViewModel> logs = new ArrayList<LogViewModel>();
        file.seek(Math.max(seekTo - currentLastOffset, 0));
        if (file.getFilePointer() > 0) {
            file.readLine(); // skip incomplete line
        }
        while (file.getFilePointer() <= seekTo) {
            logs.add(new LogViewModel(file.getFilePointer(), file.readLine()));
        }

        if (logs.size() != breakCount) {
            long newOffset = (long) (currentLastOffset * ((double) breakCount / logs.size()));
            logger.info("Read {} instead of {}. Current last offset - {}, new offset - {}", logs.size(), breakCount, currentLastOffset, newOffset);
            currentLastOffset = newOffset;
        }
        while (logs.size() > breakCount) {
            logs.remove(0);
        }
        return new LogsViewModel(seekTo - currentLastOffset, seekTo, logs);
    }
}
