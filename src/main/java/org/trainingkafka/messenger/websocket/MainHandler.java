package org.trainingkafka.messenger.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.trainingkafka.messenger.DTO.MessageDTO;
import org.trainingkafka.messenger.model.User;
import org.trainingkafka.messenger.repository.UserRepository;
import org.trainingkafka.messenger.service.MessengerService;

@RequiredArgsConstructor
public class MainHandler extends TextWebSocketHandler {
    private final UserRepository userRepository;
    private final MessengerService messengerService;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        MessageDTO messageDTO  = objectMapper.readValue(message.getPayload(), MessageDTO.class);
        User author = (User) session.getPrincipal();
        User recipient = userRepository.findById(messageDTO.getRecipientId()).orElseThrow();
        this.messengerService.sendMessage(messageDTO.getContent(), author, recipient);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }
}
