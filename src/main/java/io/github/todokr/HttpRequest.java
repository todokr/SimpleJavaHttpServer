package io.github.todokr;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private final String method;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> headers;

    HttpRequest(InputStream input) {
        // TODO
        this.method = "GET";
        this.path = "/index.html";
        this.httpVersion = "1.1";
        this.headers = new HashMap<String, String>();
    }

    String getMethod() {
        return this.method;
    }

    String getPath() {
        return this.path;
    }

    String getHttpVersion() {
        return this.httpVersion;
    }

    String getHeader(String name) {
        return this.headers.get(name);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
