package io.github.todokr;

import java.net.Socket;

import io.github.todokr.utils.Logger;

public interface ProtocolFactory {

    Runnable create(Socket socket, Logger logger);
}
