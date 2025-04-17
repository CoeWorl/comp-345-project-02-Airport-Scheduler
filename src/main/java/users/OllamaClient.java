package users;

import java.io.IOException;
import java.net.http.*;
import java.net.URI;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class OllamaClient {
    public static List<Activity> plannerRequest(List passenger) throws IOException, InterruptedException {
        String systemPrompt = "For the following topic and difficulty (on a scale of 1 to 5), " +
                "generate an original coding problem, language agnostic." +
                "For example: example input: 'arrays 3'" +
                "example output: 'Write a function that takes an array of integers and returns the sum of all even numbers.'" +
                "Please provide a similar output for the input below, and do not include any additional text.";

        String json = String.format("""
        {
        "model": "llama3.1:8b",
            "messages": [
                { "role": "user", "content": "%s %s %d"}
            ]
        }
        """, systemPrompt);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/chat"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String parsedResponse = OllamaResponseParser.parseResponse(response.body());
        return null;
    }
}