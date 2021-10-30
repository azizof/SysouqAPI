package com.sysouq.sysouqapi.config;


import com.sysouq.sysouqapi.handler.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(getHandler(), "/chat/*")
                .addInterceptors(handshakeInterceptor()).setAllowedOrigins("*");
    }

    @Bean
    public MessageHandler getHandler() {
        return new MessageHandler();
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                           WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
                // Get the URI segment corresponding to the auction id during handshake
                String path = serverHttpRequest.getURI().getPath();
                String userId = path.substring(path.lastIndexOf('/') + 1);

                // This will be added to the websocket session
                map.put("userId", userId);
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

            }
        };
    }


}
