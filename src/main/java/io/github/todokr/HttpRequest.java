package io.github.todokr;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {

    private String method;
    private String path;
    private String httpVersion;
    private Map<String, String> headers = new HashMap<>();

    private static Pattern headerPattern = Pattern.compile("^(?<key>.+?):\\s*(?<value>.+)$");

    HttpRequest(InputStream input) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String[] requestLineItems = reader.readLine().split("\\s+");
        method      = requestLineItems[0];
        path        = requestLineItems[1];
        httpVersion = requestLineItems[2];

        String pair = reader.readLine();
        while (!(pair == null || pair.isEmpty())) {
            Matcher matcher = headerPattern.matcher(pair);
            if(matcher.find()) {
                headers.put(matcher.group("key"), matcher.group("value"));
            }
            pair = reader.readLine();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
