package io.github.todokr;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import io.github.todokr.enums.Header;
import io.github.todokr.enums.Status;
import io.github.todokr.utils.Logger;

public class HttpResponse {

    private Status status;
    private String contentType;
    private OffsetDateTime lastModified;
    private int contentLength;
    private byte[] body;
    private static Logger logger = new Logger(HttpRequest.class.getSimpleName());

    public HttpResponse(Status status, String contentType, OffsetDateTime lastModified, byte[] body){
      this.status = status;
      this.contentType = contentType;
      this.lastModified = lastModified;
      this.contentLength = body.length;
      this.body = body;
    }

    public void writeTo(AsynchronousSocketChannel socket) throws IOException {

        String CRLF = "\r\n";
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;

        String header = "HTTP/1.1 " + status.statusCode + CRLF +
            Header.CONTENT_TYPE.withValue(contentType) + CRLF +
            Header.CONTENT_LENGTH.withValue(String.valueOf(contentLength)) + CRLF +
            Header.LAST_MODIFIED.withValue(formatter.format(lastModified)) + CRLF +
            Header.SERVER.withValue("SimpleJavaHTTPServer") + CRLF +
            Header.CONNECTION.withValue("Close") + CRLF +
            CRLF;

        ByteBuffer responseBuff =
            ByteBuffer.wrap(concatBytes(header.getBytes(StandardCharsets.UTF_8), body));

        socket.write(responseBuff, responseBuff, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                logger.log("write");
                if (responseBuff.hasRemaining()) {
                    socket.write(responseBuff, responseBuff, this);
                    return;
                }

                try {
                    socket.close();
                } catch (IOException e) {
                    // do nothing
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                logger.error("Failed to write");
            }
        });
    }

    private byte[] concatBytes(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;
        byte[] c = new byte[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
