package org.Modules.SubCallers;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import org.json.JSONObject;

public class ZukiImageCall {

    private String API_KEY;

    public ZukiImageCall(String API_KEY){this.API_KEY = API_KEY;}

    public String IMAGE_DATA(String prompt, int generations, String size, String model){

        JSONObject json = new JSONObject();

        json.put("prompt", prompt);
        json.put("n", generations);
        json.put("size", size);
        json.put("model", model);

        return json.toString();

    }

    public String IMAGE_CALL(String prompt, int generations, String size, String model, String endpoint){


        try {
            URL url = new URI(endpoint).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setDoOutput(true);

            String data = IMAGE_DATA(prompt, generations, size, model);

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
                String contentValue = jsonObject.getJSONArray("data").getJSONObject(0)
                        .getString("url");
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
