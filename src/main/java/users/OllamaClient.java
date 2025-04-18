// Java
package users;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import layout.Json;
import layout.Terminal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class OllamaClient {
    public static String plannerRequest(User user, Terminal t, List<Activity> activities) throws IOException, InterruptedException {
        String systemPrompt = "For the following preferences of a User and available activities at airport, give an appropriate ranked recommendation " +
                "Provide a list of activities with the following format: " +
                "activity name, activity type, activity location ; activity2 name, activity2 type, activity2 location ; activity3 name, activity3 type, activity3 location ; " +
                "I will give you Json files for the user, the terminal with its weighted graph of Businesses and the list of activities at the airport."+
                "Add nothing else to the response";

        // Construct the content by concatenating the prompt and JSON strings
        String content = systemPrompt + " " + Json.toJsonString(user) + " " +
                Json.toJsonString(t) + " " + Json.toJsonString(activities);

        // Build the JSON request using Gson objects
        JsonObject messageObject = new JsonObject();
        messageObject.addProperty("role", "user");
        messageObject.addProperty("content", content);

        JsonArray messagesArray = new JsonArray();
        messagesArray.add(messageObject);

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "llama3.1:8b");
        requestBody.add("messages", messagesArray);

        String json = new Gson().toJson(requestBody);

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