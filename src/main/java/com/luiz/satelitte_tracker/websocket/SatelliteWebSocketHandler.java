package com.luiz.satelitte_tracker.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SatelliteWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions =
            ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(
            WebSocketSession session
    ) {
        sessions.add(session);

        System.out.println(
                "WebSocket conectado: " + session.getId()
        );
    }

    public void broadcast(String message) {

        sessions.forEach(session -> {

            if (session.isOpen()) {
                try {
                    session.sendMessage(
                            new TextMessage(message)
                    );
                } catch (Exception e) {
                    System.out.println(
                            "Erro ao enviar mensagem: "
                                    + e.getMessage()
                    );
                }
            }
        });
    }
}