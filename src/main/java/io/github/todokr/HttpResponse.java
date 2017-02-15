package io.github.todokr;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class HttpResponse {

    private final Status status;
    private final String contentType;
    private final OffsetDateTime date;
    private final int contentLength;
    private final byte[] body;

    HttpResponse(Status status, String contentType, OffsetDateTime date, int contentLength, byte[] body){
      this.status = status;
      this.contentType = contentType;
      this.date = date;
      this.contentLength = body.length;
      this.body = body;
    }

    public Status getStatus() { return this.status; }
    public String getContentType() { return this.contentType; }
    public OffsetDateTime getDate() { return this.date; }
    public int getContentLength() { return this.contentLength; }
    public byte[] getBody() { return this.body; }

    public void writeTo(OutputStream out) throws IOException {

        String header = String.format(
            "HTTP/1.1 %s\r\n" +
            "Content-Type: %s\r\n" +
            "Content-Length: %d\r\n" +
            "Last-Modified: %s\r\n" +
            "Server: Java Simple HTTP Server\r\n" +
            "Connection: Close\r\n" +
            "\r\n",
            this.status.statusCode,
            this.contentType,
            this.contentLength,
            DateTimeFormatter.RFC_1123_DATE_TIME.format(this.date));

        out.write(header.getBytes(StandardCharsets.UTF_8));
        out.write(this.body);
        out.write("\r\n\r\n".getBytes(StandardCharsets.UTF_8));
        out.flush();
    }

}
