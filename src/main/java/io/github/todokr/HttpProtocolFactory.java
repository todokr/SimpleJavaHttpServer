package io.github.todokr;

import java.net.Socket;

public class HttpProtocolFactory {

    public Runnable create(Socket socket) {
        return new HttpProtocol(socket);
    }
}
