package com.luiz.satelitte_tracker.config;

import com.luiz.satelitte_tracker.websocket.SatelliteWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SatelliteWebSocketHandler satelliteWebSocketHandler;

    public WebSocketConfig(SatelliteWebSocketHandler satelliteWebSocketHandler) {
        this.satelliteWebSocketHandler = satelliteWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        System.out.println("Registrando WebSocket /ws/satellites");

        registry.addHandler(
                satelliteWebSocketHandler,
                "/ws/satellites"
        ).setAllowedOriginPatterns("*");
    }
}