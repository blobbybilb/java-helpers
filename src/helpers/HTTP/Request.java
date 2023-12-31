package helpers.HTTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class Request {
    public static String get(String url) throws Exception {
        URL obj = new URI(url).toURL();
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK)
            throw new Exception("HTTP GET request failed with response code: " + responseCode);

        return getStringFromConnection(connection);
    }

    public static String post(String url, String postData, ContentType contentType) throws Exception {
        HttpURLConnection connection = getHttpURLConnection(url, postData, contentType);
        return getResponseString(connection);
    }

    public static String post(String url, Map<String, String> postData) throws Exception {
        HttpURLConnection connection = getHttpURLConnection(url, getPostDataString(postData), ContentType.FORM);
        return getResponseString(connection);
    }

    private static String getPostDataString(Map<String, String> postData) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : postData.entrySet()) {
            if (!result.isEmpty()) {
                result.append("&");
            }
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }
        return result.toString();
    }

    private static String getStringFromConnection(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) response.append(inputLine);
        in.close();
        connection.disconnect();
        return response.toString();
    }

    private static String getResponseString(HttpURLConnection connection) throws Exception {
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return getStringFromConnection(connection);
        } else {
            throw new Exception("HTTP POST request failed with response code: " + responseCode);
        }
    }

    private static HttpURLConnection getHttpURLConnection(String url, String postData, ContentType contentType) throws URISyntaxException, IOException {
        URL obj = new URI(url).toURL();
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", contentType.getValue());
        connection.setRequestProperty("Content-Length", Integer.toString(postData.getBytes().length));
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(postData);
            wr.flush();
        }
        return connection;
    }
}
