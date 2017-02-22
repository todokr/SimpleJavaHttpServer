package io.github.todokr;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import io.github.todokr.enums.ContentType;
import io.github.todokr.enums.Header;
import io.github.todokr.enums.Status;

public class HttpRequestHandler {

    protected static final String PUBLIC_DIR_NAME = "public";
    protected static final String INDEX_FILE_NAME = "index.html";
    protected static final String NOTFOUND_FILE_NAME = "404.html";

    private static byte[] EMPTY_BODY = {};

    public HttpResponse handleRequest(HttpRequest request) throws IOException {

        Status status;
        Path path;
        Path pathToTest = Paths.get(PUBLIC_DIR_NAME + request.getPath()).normalize();
        if (Files.isRegularFile(pathToTest)) {
            path = pathToTest;
            status = Status.OK;
        } else if (Files.isDirectory(pathToTest) && Files.isRegularFile(pathToTest.resolve(INDEX_FILE_NAME))) {
            path = pathToTest.resolve(INDEX_FILE_NAME);
            status = Status.OK;
        } else {
            path = Paths.get(PUBLIC_DIR_NAME).resolve(NOTFOUND_FILE_NAME);
            status = Status.NOT_FOUND;
        }

        String contentType;
        String mimeFromName;
        String mimeFromContent;
        if ((mimeFromName = URLConnection.guessContentTypeFromName(path.toString())) != null) {
            contentType = mimeFromName;
        } else if ((mimeFromContent = URLConnection.guessContentTypeFromStream(Files.newInputStream(path))) != null) {
            contentType = mimeFromContent;
        } else {
            contentType = ContentType.OCTET_STREAM.value;
        }

        OffsetDateTime lastModified = OffsetDateTime.ofInstant(Instant.ofEpochMilli(path.toFile().lastModified()), ZoneOffset.UTC);
        String ifModifiedSinceHeader = request.getHeader(Header.IF_MODIFIED_SINCE.key);
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        if(ifModifiedSinceHeader == null || lastModified.isBefore(OffsetDateTime.parse(ifModifiedSinceHeader, formatter))) {
            return new HttpResponse(status, contentType, lastModified, Files.readAllBytes(path));
        } else {
            return new HttpResponse(Status.NOT_MODIFIED, contentType, lastModified, EMPTY_BODY);
        }
    }
}
