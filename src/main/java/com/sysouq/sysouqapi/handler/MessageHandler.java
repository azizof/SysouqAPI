package com.sysouq.sysouqapi.handler;

import com.google.gson.Gson;
import com.sysouq.sysouqapi.dto.MessageDTO;
import com.sysouq.sysouqapi.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class MessageHandler extends TextWebSocketHandler {

    @Autowired
    private MessageService messageService;

    private static final Object LOCK = new Object();
    private final Map<Long, WebSocketSession> sessions = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        System.out.println("received " + textMessage.getPayload());
        Gson gson = new Gson();
        MessageDTO msg = gson.fromJson(textMessage.getPayload(), MessageDTO.class);

        MessageDTO message = messageService.addMessage(msg.getSenderId(), msg.getRecipientId(),
                msg.getConversationId(), msg.getBody());
        sendMessage(message.getRecipientId(), gson.toJson(message));
    }

    public void sendMessage(Long destId, String json) {
        WebSocketSession s;
        synchronized (LOCK) {
            s = sessions.get(destId);
        }
        if (s != null && s.isOpen()) {
            try {
                s.sendMessage(new TextMessage(json));
            } catch (IOException e) {
                synchronized (LOCK) {
                    sessions.remove(destId);
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = Long.parseLong((String) session.getAttributes().get("userId"));
        synchronized (LOCK) {
            if (!sessions.containsKey(userId)) {
                sessions.put(userId, session);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(Long.parseLong((String) session.getAttributes().get("userId")));
    }


}
