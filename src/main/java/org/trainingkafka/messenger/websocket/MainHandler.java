package org.trainingkafka.messenger.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.trainingkafka.messenger.repository.UserRepository;
import org.trainingkafka.messenger.service.MessengerService;

@RequiredArgsConstructor
public class MainHandler extends TextWebSocketHandler {
    private final UserRepository userRepository;
    private final MessengerService messengerService;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = Long.parseLong(session.getHandshakeHeaders().get("x-userid").get(0));
        this.messengerService.removeSession(userRepository.getReferenceById(userId));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(session.getHandshakeHeaders());
        Long userId = Long.parseLong(session.getHandshakeHeaders().get("x-userid").get(0));
        this.messengerService.addSession(userRepository.getReferenceById(userId), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        this.messengerService.sendMessage(payload, userRepository.getReferenceById(1L), userRepository.getReferenceById(1L));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }
}
