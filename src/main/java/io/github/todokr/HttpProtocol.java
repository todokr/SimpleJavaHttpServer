package io.github.todokr;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class HttpProtocol implements Runnable {

    private Socket socket;
    private HttpRequestHandler requestHandler = new HttpRequestHandler();
    private static Logger logger = Logger.getLogger(HttpProtocol.class.getName());

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
                socket.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }
}
