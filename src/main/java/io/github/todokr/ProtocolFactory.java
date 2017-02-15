package io.github.todokr;

import java.net.Socket;

public interface ProtocolFactory {

    Runnable create(Socket socket);
}
