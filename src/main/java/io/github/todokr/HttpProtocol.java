package io.github.todokr;

import java.io.IOException;
import java.net.Socket;

import io.github.todokr.utils.Logger;

public class HttpProtocol implements Runnable {

    private static Logger logger = new Logger(HttpProtocol.class.getSimpleName());
    private Socket socket;
    private HttpRequestHandler requestHandler = new HttpRequestHandler();

    public HttpProtocol(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (Socket s = socket) {
            HttpRequest request = new HttpRequest(s.getInputStream());
            HttpResponse response = requestHandler.handleRequest(request);
            response.writeTo(s.getOutputStream());
        } catch (IOException e) {
            logger.error("Failed to process: " + e.getMessage());
        }
    }
}
