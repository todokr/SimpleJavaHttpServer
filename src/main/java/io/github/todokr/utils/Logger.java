package io.github.todokr.utils;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private PrintStream out;
    private String loggerName;

    public Logger(PrintStream out, String loggerName) {
        this.out = out;
        this.loggerName = loggerName;
    }

    public void log(String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        out.printf("[%s] %s\t%s\r\n", loggerName, now.format(formatter), message);
    }
}
