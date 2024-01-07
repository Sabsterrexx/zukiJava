package org.Modules.SubCallers;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import org.json.JSONObject;

public class ZukiChatCall {

    private String API_KEY;

    public ZukiChatCall(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    public String CHAT_DATA(String userName, String userMessage, String requestedModel, String systemPrompt, double currTemp) {
        // Gets the actual data object being sent to the API.

        String data = "{\n" +
                "    \"model\": \"" + requestedModel + "\",\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"system\",\n" +
                "            \"content\": \"" + systemPrompt + "\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \"" + systemPrompt + "\\n Here is a message a user called " + userName +
                " sent you: " + userMessage + "\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"temperature\": " + currTemp + "\n" +
                "}";
        return data;
    }

    public String CHAT_CALL(String userName, String userMessage, String requestedModel, String systemPrompt, double currTemp, String endpoint) {
        // Makes an API call via the desired endpoint.

        try {
            URL url = new URI(endpoint).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setDoOutput(true);

            String data = CHAT_DATA(userName, userMessage, requestedModel, systemPrompt, currTemp);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = data.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Main response.

                // Reading the response
                java.util.Scanner scanner = new java.util.Scanner(connection.getInputStream(), "UTF-8");
                String responseData = scanner.useDelimiter("\\A").next();

                //Convert to JSON object:
                JSONObject jsonObject = new JSONObject(responseData);

                // Access the desired value
                String contentValue = jsonObject.getJSONArray("choices").getJSONObject(0)
                        .getJSONObject("message").getString("content");
                scanner.close();

                return contentValue;
            } else {
                System.out.println("HTTP error code: " + responseCode);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return null;
    }
}