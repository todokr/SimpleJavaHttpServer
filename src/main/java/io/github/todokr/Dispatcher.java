package io.github.todokr;

import java.net.ServerSocket;

import io.github.todokr.utils.Logger;

public interface Dispatcher {

    void startDispatching(ServerSocket serverSocket, Logger logger, ProtocolFactory protocolFactory);
}
