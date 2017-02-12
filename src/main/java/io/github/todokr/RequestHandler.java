package io.github.todokr;

import java.io.IOException;

public interface RequestHandler {

    HttpResponse handleRequest(HttpRequest request) throws IOException;
}
