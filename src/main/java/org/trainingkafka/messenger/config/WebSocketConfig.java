package org.trainingkafka.messenger.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.trainingkafka.messenger.model.User;
import org.trainingkafka.messenger.repository.UserRepository;
import org.trainingkafka.messenger.service.MessengerService;
import org.trainingkafka.messenger.websocket.MainHandler;

import java.security.Principal;
import java.util.Map;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final MessengerService messengerService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler(), "/messenger").setHandshakeHandler(new MainHandshakeHandler(this.userRepository));
    }
    @Bean
    public WebSocketHandler handler(){
        return new MainHandler(this.userRepository, this.messengerService, this.objectMapper);
    }
    private static class MainHandshakeHandler extends DefaultHandshakeHandler {
        private final UserRepository userRepository;
        public MainHandshakeHandler(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
            Long userId = Long.parseLong(request.getHeaders().get("x-userid").get(0));
            return this.userRepository.findById(userId).orElseThrow(() -> new HandshakeFailureException("User not found"));
        }
    }
}
