package io.github.todokr.enums;

public enum Header {
    ContentType("Content-Type"),
    ContentLength("Content-Length"),
    LAST_MODIFIED("Last-Modified"),
    Connection("Connection"),
    Server("Server"),
    IF_MODIFIED_SINCE("If-Modified-Since");

    public final String key;
    Header(String key) {
        this.key = key;
    }

    public String withValue(String value) {
        return this.key + ": " + value;
    }
}
