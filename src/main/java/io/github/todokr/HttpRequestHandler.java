package io.github.todokr;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;

public class HttpRequestHandler implements RequestHandler {

    protected final String PUBLIC_DIR_NAME = "public";
    protected final String INDEX_FILE_NAME = "index.html";
    protected final String NOTFOUND_FILE_NAME = "404.html";

    public HttpResponse handleRequest(HttpRequest request) throws IOException {

        final Status status;
        Path path = Paths.get(PUBLIC_DIR_NAME + request.getPath()).normalize();
        if (Files.isRegularFile(path)) {
            status = Status.OK;
        } else if (Files.isDirectory(path) && Files.isRegularFile(path.resolve(INDEX_FILE_NAME))) {
            path = path.resolve(INDEX_FILE_NAME);
            status = Status.OK;
        } else {
            path = Paths.get(PUBLIC_DIR_NAME).resolve(NOTFOUND_FILE_NAME);
            status = Status.NotFound;
        }

        final String contentType;
        final String mimeFromName;
        final String mimeFromContent;
        if ((mimeFromName = URLConnection.guessContentTypeFromName(path.toString())) != null) {
            contentType = mimeFromName;
        } else if ((mimeFromContent = URLConnection.guessContentTypeFromStream(Files.newInputStream(path))) != null) {
            contentType = mimeFromContent;
        } else {
            contentType = "application/octet-stream";
        }

        byte[] body = Files.readAllBytes(path);

        return new HttpResponse(status, contentType, OffsetDateTime.now(), body.length, body);
    }
}
