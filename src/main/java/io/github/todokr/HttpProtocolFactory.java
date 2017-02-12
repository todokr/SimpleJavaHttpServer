package io.github.todokr;

import java.net.Socket;

import io.github.todokr.utils.Logger;

public class HttpProtocolFactory implements ProtocolFactory {

    public Runnable create(Socket socket, Logger logger) {
        return new HttpProtocol(socket, logger);
    }
}
