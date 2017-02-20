package io.github.todokr;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class HttpProtocol implements Runnable {

    private final Socket socket;
    private final HttpRequestHandler requestHandler = new HttpRequestHandler();
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    HttpProtocol(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            HttpRequest request = new HttpRequest(socket.getInputStream());
            HttpResponse response = requestHandler.handleRequest(request);
            response.writeTo(socket.getOutputStream());
        } catch (IOException e) {
            logger.severe("Failed to process: " + e.getMessage());
        } finally {
            try {
                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }
}
