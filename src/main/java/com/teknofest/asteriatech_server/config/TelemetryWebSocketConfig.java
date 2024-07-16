package com.teknofest.asteriatech_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class TelemetryWebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(telemetryWebSocketHandler(), "/telemetry")
                .setAllowedOrigins("*") // Güvenlik için IP veya domain listeleme yapılabilir
                .addInterceptors(new HttpSessionHandshakeInterceptor()); // Opsiyonel: Handshake aşamasında session bilgilerini yakalamak için
    }

    @Bean
    public TelemetryWebSocketHandler telemetryWebSocketHandler() {
        return new TelemetryWebSocketHandler();
    }
}
