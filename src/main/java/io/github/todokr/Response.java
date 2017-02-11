package io.github.todokr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.OffsetDateTime;

public class Response {

    private final String httpVersion;
    private final String statusCode;
    private final String contentType; // TODO ContentType type
    private final OffsetDateTime date;
    private final String server = "Java Simple HTTP Server";
    private final byte[] body;

    public Response(Request request) {
        this.httpVersion = "1.1";
        this.statusCode = "200 OK";
        this.contentType = "text/plain";
        this.date = OffsetDateTime.now();
        this.body = "Hello, Java Simple HTTP Server\r\n".getBytes();
    }

    public void writeTo(OutputStream out) throws IOException {

        String header = // TODO
            "HTTP/1.1 200 OK\r\n" +
            "Date: Sat, 11 Feb 2017 05:28:55 GMT\r\n" +
            "Server: Apache\r\n" +
            "Connection: close\r\n" +
            "Content-Type: text/html; charset=UTF-8\r\n" +
            "\r\n";

        out.write(header.getBytes());
        out.write(this.body);
    }


}
