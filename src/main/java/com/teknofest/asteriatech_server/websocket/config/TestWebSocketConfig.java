package com.teknofest.asteriatech_server.websocket.config;

import com.teknofest.asteriatech_server.service.ThermalCameraObjectDetectionService;
import com.teknofest.asteriatech_server.websocket.handler.CentralVisionControlWebSocketHandler;
import com.teknofest.asteriatech_server.websocket.handler.DroneWebSocketHandler;
import com.teknofest.asteriatech_server.websocket.handler.TelemetryWebSocketHandler;
import com.teknofest.asteriatech_server.websocket.handler.ThermalCameraWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class TestWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(centralVisionControlWebSocketHandler(), "central-vision-action")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor());

        registry.addHandler(droneWebSocketHandler(), "drone-action")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor());

        registry.addHandler(telemetryWebSocketHandler(), "telemetry")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor());

        registry.addHandler(applicationContext.getBean(ThermalCameraWebSocketHandler.class), "thermal-camera")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

    @Bean
    public CentralVisionControlWebSocketHandler centralVisionControlWebSocketHandler() {
        return new CentralVisionControlWebSocketHandler();
    }

    @Bean
    public DroneWebSocketHandler droneWebSocketHandler() {
        return new DroneWebSocketHandler();
    }

    @Bean
    public TelemetryWebSocketHandler telemetryWebSocketHandler() {
        return new TelemetryWebSocketHandler();
    }

    @Bean
    public ThermalCameraWebSocketHandler thermalCameraWebSocketHandler(ThermalCameraObjectDetectionService service) {
        return new ThermalCameraWebSocketHandler(service);
    }
}
