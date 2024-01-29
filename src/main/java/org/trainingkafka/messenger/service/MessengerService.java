package org.trainingkafka.messenger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.trainingkafka.messenger.model.Message;
import org.trainingkafka.messenger.model.User;
import org.trainingkafka.messenger.repository.MessageRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessengerService {
    private final HashMap<User, WebSocketSession> sessions = new HashMap<>();
    private final MessageRepository messageRepository;

    //TODO: implement handshake handler
    public void addSession(User user, WebSocketSession session) {
        sessions.put(user, session);
    }
    public void removeSession(User user) {
        sessions.remove(user);
    }
    public void sendMessage(String message, User author, User recipient){
        messageRepository.save(new Message(message, author, recipient));
    }

    public List<Message> getMessages(User author, User recipient){
        return messageRepository.findMessagesByAuthorAndRecipient(author, recipient);
    }
}
