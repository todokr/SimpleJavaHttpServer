package io.github.todokr.enums;

public enum ContentType {
    HTML("text/html"),
    OCTET_STREAM("application/octet-stream");

    public final String value;
    ContentType(String value) {
        this.value = value;
    }
}
