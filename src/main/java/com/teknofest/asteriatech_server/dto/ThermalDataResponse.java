package com.teknofest.asteriatech_server.dto;

public class ThermalDataResponse {
    private String thermalData;
    private String detectStatus;

    public ThermalDataResponse(String thermalData, String detectStatus) {
        this.thermalData = thermalData;
        this.detectStatus = detectStatus;
    }

    public String getThermalData() {
        return thermalData;
    }

    public void setThermalData(String thermalData) {
        this.thermalData = thermalData;
    }

    public String getDetectStatus() {
        return detectStatus;
    }

    public void setDetectStatus(String detectStatus) {
        this.detectStatus = detectStatus;
    }
}
