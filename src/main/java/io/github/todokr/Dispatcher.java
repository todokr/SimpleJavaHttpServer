package io.github.todokr;

import java.net.ServerSocket;

public interface Dispatcher {

    void startDispatching(ServerSocket serverSocket, ProtocolFactory protocolFactory);
}
