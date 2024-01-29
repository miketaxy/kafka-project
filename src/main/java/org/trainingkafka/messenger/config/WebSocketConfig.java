package org.trainingkafka.messenger.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.trainingkafka.messenger.repository.UserRepository;
import org.trainingkafka.messenger.service.MessengerService;
import org.trainingkafka.messenger.websocket.MainHandler;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final MessengerService messengerService;
    private final UserRepository userRepository;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler(), "/messenger");
    }
    @Bean
    public WebSocketHandler handler(){
        return new MainHandler(this.userRepository, this.messengerService);
    }
}
