package com.epicode.progettofinaleepicode.api;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service 
public class RugbyApiService {
	
	private final AsyncHttpClient client;
	
	@Value("${api.rapidapi.key}")
    private String apiKey;  // Load API key from application.properties

    @Value("${api.rapidapi.host}")
    private String apiHost;

    public RugbyApiService() {
        // Initialize AsyncHttpClient
        this.client = new DefaultAsyncHttpClient();
    }
	
    public CompletableFuture<String> getCompetitions() {
        // Prepare the API call
        return client.prepare("GET", "https://football98.p.rapidapi.com/competitions" )
                .setHeader("x-rapidapi-key", apiKey)
                .setHeader("x-rapidapi-host", apiHost)
                .execute()
                .toCompletableFuture()
                .thenApply(response -> {
                    if (response.getStatusCode() != 200) {
                        throw new RuntimeException("Error fetching data: " + response.getStatusText());
                    }
                    return response.getResponseBody();
                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return "Error: " + ex.getMessage();
                });
    }

    // Ensure client is closed when done
    public void closeClient() throws IOException {
        client.close();
    }

}
