package io.github.todokr;


public enum ContentType {
    HTML("text/html");

    public final String mime;
    private ContentType(String mime) {
        this.mime = mime;
    }
}
