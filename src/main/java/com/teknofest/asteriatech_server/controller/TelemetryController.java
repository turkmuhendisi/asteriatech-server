package com.teknofest.asteriatech_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TelemetryController {

    @MessageMapping("/telemetry")
    @SendTo("/topic/telemetry")
    public String handleTelemetryData(String message) {
        System.out.println("Received telemetry data: " + message);
        return message;
    }
}
