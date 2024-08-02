package com.teknofest.asteriatech_server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CentralVisionIncomingJsonSplitService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getActionCommand(String json) throws Exception {
        json = cleanJsonString(json);  // JSON verisini temizle
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode actionCommandNode = rootNode.get("actionCommand");
        if (actionCommandNode != null) {
            return actionCommandNode.asText().toLowerCase();
        } else {
            throw new Exception("JSON does not contain actionCommand property");
        }
    }

    private static String cleanJsonString(String jsonString) {
        return jsonString.replaceAll("[^\\x20-\\x7E]", "");
    }
}
