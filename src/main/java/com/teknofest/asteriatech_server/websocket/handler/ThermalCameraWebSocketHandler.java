package com.teknofest.asteriatech_server.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teknofest.asteriatech_server.dto.ThermalDataResponse;
import com.teknofest.asteriatech_server.service.ThermalCameraObjectDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class ThermalCameraWebSocketHandler extends TextWebSocketHandler {

    private ThermalCameraObjectDetectionService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Autowired
    public ThermalCameraWebSocketHandler(ThermalCameraObjectDetectionService service) {
        this.service = service;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String detectStatus = service.processThermalData(payload);
        ThermalDataResponse response = new ThermalDataResponse(payload, detectStatus);
        String responseJson = objectMapper.writeValueAsString(response);
        sendThermalDataToClients(responseJson);
        System.out.println(responseJson);
    }

    public void sendThermalDataToClients(String json) throws Exception {
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(json));
                }
            }
        }
    }
}