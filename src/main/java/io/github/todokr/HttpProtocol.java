package io.github.todokr;

import java.io.IOException;
import java.net.Socket;

import io.github.todokr.utils.Logger;

public class HttpProtocol implements Runnable {

    private static Logger logger = new Logger(HttpProtocol.class.getSimpleName());
    private Socket socket;
    private HttpRequestHandler requestHandler = new HttpRequestHandler();

    HttpProtocol(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            HttpRequest request = new HttpRequest(socket.getInputStream());
            HttpResponse response = requestHandler.handleRequest(request);
            response.writeTo(socket.getOutputStream());
        } catch (IOException e) {
            logger.error("Failed to process: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }
}
