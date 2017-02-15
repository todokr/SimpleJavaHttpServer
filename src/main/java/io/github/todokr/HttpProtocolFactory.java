package io.github.todokr;

import java.net.Socket;

public class HttpProtocolFactory implements ProtocolFactory {

    public Runnable create(Socket socket) {
        return new HttpProtocol(socket);
    }
}
