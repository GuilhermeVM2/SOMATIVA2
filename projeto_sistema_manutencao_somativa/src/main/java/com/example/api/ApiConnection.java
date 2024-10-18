package com.example.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiConnection<BufferedWriter> {
    private static final String API_URL = "http://localhost:3000/";

    public static String getData(String endpoint) {
        try {
            URL url = new URL(API_URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();
            return content.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching data: " + e.getMessage(); // Mensagem de erro específica
        }
    }




    public static String postData(String endpoint, String jsonData) {
    try {
    URL url = new URL(API_URL + endpoint);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/json; utf-8");
    connection.setRequestProperty("Accept", "application/json");
    connection.setDoOutput(true);

    try (OutputStream os = connection.getOutputStream()) {
    byte[] input = jsonData.getBytes("utf-8");
    os.write(input, 0, input.length);
    }

    BufferedReader in = new BufferedReader(new
    InputStreamReader(connection.getInputStream(), "utf-8"));
    StringBuilder response = new StringBuilder();
    String responseLine;

    while ((responseLine = in.readLine()) != null) {
    response.append(responseLine.trim());
    }

    in.close();
    connection.disconnect();
    return response.toString();

    } catch (Exception e) {
    e.printStackTrace();
    return "Error posting data: " + e.getMessage(); // Mensagem de erro específica
    }
    }

    public static String putData(String endpoint, String jsonData) {
        try {
            URL url = new URL(API_URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine.trim());
            }

            in.close();
            connection.disconnect();
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating data: " + e.getMessage(); // Mensagem de erro específica
        }
    }

    public static String deleteData(String endpoint) {
        try {
            URL url = new URL(API_URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT || responseCode == HttpURLConnection.HTTP_OK) {
                return "Resource deleted successfully.";
            } else {
                return "Failed to delete resource. Response code: " + responseCode;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting data: " + e.getMessage(); // Mensagem de erro específica
        }
    }
}
