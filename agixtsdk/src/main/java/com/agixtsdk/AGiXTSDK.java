package com.agixtsdk;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class AGiXTSDK {
    private static final String DEFAULT_BASE_URI = "http://localhost:7437";
    private final String baseUri;
    private final String apiKey;

    public AGiXTSDK(String baseUri, String apiKey) {
        this.baseUri = baseUri == null ? DEFAULT_BASE_URI : baseUri;
        this.apiKey = apiKey;
    }

    private String handleError(Exception e) {
        System.out.println("Error: " + e.getMessage());
        throw new RuntimeException("Unable to retrieve data. " + e.getMessage());
    }

    private <T> T parseResponse(CloseableHttpResponse response, Class<T> valueType) throws IOException {
        String responseBody = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        return mapper.readValue(responseBody, valueType);
    }

    @SuppressWarnings("unchecked")
    public List<String> getProviders() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(baseUri + "/api/provider");
            httpGet.setHeader("Authorization", "Bearer " + apiKey);
            httpGet.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Map<String, Object> responseMap = parseResponse(response, Map.class);
                return (List<String>) responseMap.get("providers");
            }
        } catch (Exception e) {
            return Collections.singletonList(handleError(e));
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getProvidersByService(String service) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(baseUri + "/api/providers/service/" + service);
            httpGet.setHeader("Authorization", "Bearer " + apiKey);
            httpGet.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Map<String, Object> responseMap = parseResponse(response, Map.class);
                return (List<String>) responseMap.get("providers");
            }
        } catch (Exception e) {
            return Collections.singletonList(handleError(e));
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getProviderSettings(String providerName) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(baseUri + "/api/provider/" + providerName);
            httpGet.setHeader("Authorization", "Bearer " + apiKey);
            httpGet.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Map<String, Object> responseMap = parseResponse(response, Map.class);
                return (Map<String, Object>) responseMap.get("settings");
            }
        } catch (Exception e) {
            return Collections.singletonMap("error", handleError(e));
        }
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getAgents() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(baseUri + "/api/agent");
            httpGet.setHeader("Authorization", "Bearer " + apiKey);
            httpGet.setHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Map<String, Object> responseMap = parseResponse(response, Map.class);
                Object agents = responseMap.get("agents");
                if (agents instanceof List) {
                    return (List<Map<String, Object>>) agents;
                } else {
                    throw new RuntimeException("Unable to retrieve agents. Invalid response format.");
                }
            }
        } catch (Exception e) {
            handleError(e);
            return Collections.emptyList();
        }
    }

    public static void main(String[] args) {
        AGiXTSDK sdk = new AGiXTSDK("http://localhost:7437", "your_api_key");
        List<String> providers = sdk.getProviders();
        System.out.println("Providers: " + providers);

        List<Map<String, Object>> agents = sdk.getAgents();
        for (Map<String, Object> agent : agents) {
            System.out.println("Agent: " + agent);
        }
    }
}
