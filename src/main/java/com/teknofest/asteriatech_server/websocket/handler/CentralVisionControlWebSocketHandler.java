package com.teknofest.asteriatech_server.websocket.handler;

import com.teknofest.asteriatech_server.enums.CentralVisionAction;
import com.teknofest.asteriatech_server.service.CentralVisionIncomingJsonSplitService;
import com.teknofest.asteriatech_server.service.MessageValidationService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class CentralVisionControlWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received payload: " + payload); // Gelen mesajı loglayın

        try {
            String actionCommand = CentralVisionIncomingJsonSplitService.getActionCommand(payload);
            if (MessageValidationService.isValidAction(CentralVisionAction.class, actionCommand)) {
                sendCentralVisionAction(actionCommand);
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.sendMessage(new TextMessage("Invalid JSON format"));
        }
    }

    public void sendCentralVisionAction(String json) throws Exception {
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(json));  // JSON string formatında gönder
                }
            }
        }
    }

    /**
     * @param session
     * @throws Exception just for test
     */
    /*public void sendMessageToClientFromServer(WebSocketSession session, String message) throws Exception {
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }*/

}
