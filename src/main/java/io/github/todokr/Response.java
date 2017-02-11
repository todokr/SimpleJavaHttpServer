package io.github.todokr;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import javax.activation.MimeType;

public class Response {

    private final String PUBLIC_DIR_NAME = "public";
    private final String INDEX_FILE_NAME = "index.html";
    private final String NOTFOUND_FILE_NAME = "404.html";

    private final Status status;
    private final String contentType;
    private final OffsetDateTime date;
    private final byte[] body;

    public Response(Request request) throws IOException {

        Path path = Paths.get(PUBLIC_DIR_NAME + request.getPath()).normalize();
        System.out.println(path);
        if (Files.isRegularFile(path)) {
          this.status = Status.OK;
        } else if (Files.isDirectory(path) && Files.isRegularFile(path.resolve(INDEX_FILE_NAME))) {
            path = path.resolve(INDEX_FILE_NAME);
            this.status = Status.OK;
        } else {
            path = Paths.get(PUBLIC_DIR_NAME).resolve(NOTFOUND_FILE_NAME);
            this.status = Status.NotFound;
        }

        final String mimeFromName;
        final String mimeFromContent;
        if ((mimeFromName = URLConnection.guessContentTypeFromName(path.toString())) != null) {
            this.contentType = mimeFromName;
        } else if ((mimeFromContent = URLConnection.guessContentTypeFromStream(Files.newInputStream(path))) != null) {
            this.contentType = mimeFromContent;
        } else {
            this.contentType = "application/octet-stream";
        }

        this.date = OffsetDateTime.now();
        this.body = Files.readAllBytes(path);
    }

    public void writeTo(OutputStream out) throws IOException {

        String header = String.format(
            "HTTP/1.1 %s\r\n" +
            "Content-Type: %s\r\n" +
            "Server: Java Simple HTTP Server\r\n" +
            "Connection: Close\r\n" +
            "\r\n",
            this.status,
            this.contentType);

        out.write(header.getBytes());
        out.write(this.body);
        out.flush();
    }

}
