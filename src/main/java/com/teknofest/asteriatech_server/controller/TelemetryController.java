package com.teknofest.asteriatech_server.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
