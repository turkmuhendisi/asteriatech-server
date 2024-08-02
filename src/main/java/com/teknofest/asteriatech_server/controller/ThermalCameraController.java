package com.teknofest.asteriatech_server.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class ThermalCameraController {

    private SimpMessagingTemplate messagingTemplate;

    public ThermalCameraController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMatrix")
    public void sendMatrixToClient(@RequestBody List<Double> matrixData) {
        messagingTemplate.convertAndSend("/topic/thermal", matrixData);
    }
}
