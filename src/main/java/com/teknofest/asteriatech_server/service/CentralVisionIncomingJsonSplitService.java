package com.teknofest.asteriatech_server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CentralVisionIncomingJsonSplitService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getActionCommand(String json) throws Exception {
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode actionCommandNode = rootNode.get("actionCommand");
        if (actionCommandNode != null) {
            return actionCommandNode.asText();
        } else {
            throw new Exception("JSON does not contain actionCommand property");
        }
    }
}
