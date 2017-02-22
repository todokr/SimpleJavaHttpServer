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

        final Status status;
        final Path path;
        final Path pathToTest = Paths.get(PUBLIC_DIR_NAME + request.getPath()).normalize();
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

        final String contentType;
        final String mimeFromName;
        final String mimeFromContent;
        if ((mimeFromName = URLConnection.guessContentTypeFromName(path.toString())) != null) {
            contentType = mimeFromName;
        } else if ((mimeFromContent = URLConnection.guessContentTypeFromStream(Files.newInputStream(path))) != null) {
            contentType = mimeFromContent;
        } else {
            contentType = ContentType.OCTET_STREAM.value;
        }

        final OffsetDateTime lastModified = OffsetDateTime.ofInstant(Instant.ofEpochMilli(path.toFile().lastModified()), ZoneOffset.UTC);
        final String ifModifiedSinceHeader = request.getHeader(Header.IF_MODIFIED_SINCE.key);
        final DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        if (!(ifModifiedSinceHeader == null || lastModified.isAfter(OffsetDateTime.parse(ifModifiedSinceHeader, formatter)))) {
            // クライアントの最終リソース取得日時が、返却するファイルの更新日時以前の場合、クライアントキャッシュを利用させるためにステータスコード304で返す
            return new HttpResponse(Status.NOT_MODIFIED, contentType, lastModified, EMPTY_BODY);
        } else {
            return new HttpResponse(status, contentType, lastModified, Files.readAllBytes(path));
        }
    }
}
