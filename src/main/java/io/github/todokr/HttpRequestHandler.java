package io.github.todokr;

import java.io.IOException;

public class HttpRequestHandler implements RequestHandler {

    public HttpResponse handleRequest(HttpRequest request) throws IOException {
        return new HttpResponse(request);
    }
}
