package io.github.todokr;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {

    private final String method;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> headers = new HashMap<>();

    HttpRequest(InputStream input) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(input)));

        String[] requestLineItems = reader.readLine().split("\\s+");
        this.method      = requestLineItems[0];
        this.path        = requestLineItems[1];
        this.httpVersion = requestLineItems[2];

        Pattern headerPattern = Pattern.compile("(?<key>\\S+)\\s*:\\s*(?<value>\\S+)");
        String pair = reader.readLine();
        while (pair != null || pair.isEmpty()) {
            Matcher matcher = headerPattern.matcher(pair);
            if(matcher.find()) {
                this.headers.put(matcher.group("key"), matcher.group("value"));
            }
            pair = reader.readLine();
        }
    }

    String getMethod() {
        return this.method;
    }

    String getPath() {
        return this.path;
    }

    String getHttpVersion() {
        return this.httpVersion;
    }

    String getHeader(String name) {
        return this.headers.get(name);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
