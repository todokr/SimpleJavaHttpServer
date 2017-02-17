package io.github.todokr;

import io.github.todokr.enums.Header;
import io.github.todokr.enums.Status;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class HttpResponse {

    private final Status status;
    private final String contentType;
    private final OffsetDateTime lastModified;
    private final int contentLength;
    private final byte[] body;

    HttpResponse(Status status, String contentType, OffsetDateTime lastModified, byte[] body){
      this.status = status;
      this.contentType = contentType;
      this.lastModified = lastModified;
      this.contentLength = body.length;
      this.body = body;
    }

    public void writeTo(OutputStream out) throws IOException {

        String CRLF = "\r\n";
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        String header =
                "HTTP/1.1 " + this.status.statusCode + CRLF +
                Header.ContentType.withValue(this.contentType) + CRLF +
                Header.ContentLength.withValue(String.valueOf(this.contentLength)) + CRLF +
                Header.LAST_MODIFIED.withValue(formatter.format(this.lastModified)) + CRLF +
                Header.Server.withValue("SimpleJavaHTTPServer") + CRLF +
                Header.Connection.withValue("Close") + CRLF +
                CRLF;

        out.write(header.getBytes(StandardCharsets.UTF_8));
        out.write(this.body);
        out.write((CRLF + CRLF).getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
}
