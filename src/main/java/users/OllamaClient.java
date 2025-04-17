package users;

import layout.Json;
import layout.Terminal;

import java.io.IOException;
import java.net.http.*;
import java.net.URI;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class OllamaClient {
    public static String plannerRequest(User user, Terminal t, List<Activity> activities) throws IOException, InterruptedException {
        String systemPrompt = "For the following preferences of a User and available activities at airport, give an appropriate ranked recommendation" +
                "Provide a list of activities with the following format: " +
                "activity name, activity type, activity location ; activity2 name, activity2 type, activity2 location ; activity3 name, activity3 type, activity3 location ; " +
                "I will give you Json files for the user, the terminal with its weighted graph of Businesses and the list of activities at the airport.";

        String json = String.format("""
        {
        "model": "llama3.1:8b",
            "messages": [
                { "role": "user", "content": "%s %s %s %s"}
            ]
        }
        """, systemPrompt, Json.toJsonString(user), Json.toJsonString(t), Json.toJsonString(activities));
        json = json.replace("\r", "");
        json = json.replace("\n", "");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/chat"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        System.err.println(response.body());
        return OllamaResponseParser.parseResponse(response.body());
    }
}