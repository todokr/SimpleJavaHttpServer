package io.github.todokr;

import java.io.IOException;

public class HttpRequestHandler implements RequestHandler {

    public Response handleRequest(Request request) throws IOException {
        return new Response(request);
    }
}
