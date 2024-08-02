package com.teknofest.asteriatech_server.service;

import org.springframework.stereotype.Service;

@Service
public class ThermalCameraObjectDetectionService {

    public String processThermalData(String payload) {
        // '[' ve ']' karakterlerini temizle
        payload = payload.replace("[", "").replace("]", "");

        String[] values = payload.split(",");
        int count = 0;

        for (String value : values) {
            double doubleValue = Double.parseDouble(value.trim());
            if (doubleValue > 35 && doubleValue < 40) {
                count++;
            }
        }

        return count >= 30 ? "Detected" : "Not Detected";
    }
}
