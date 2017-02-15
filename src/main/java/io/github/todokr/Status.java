package io.github.todokr;

public enum Status {
    OK("200 OK"),
    NotFound("404 NotFound"),
    InternalServerError("500 InternalServerError");

    public final String statusCode;

    Status(String statusCode) {
        this.statusCode = statusCode;
    }
}
