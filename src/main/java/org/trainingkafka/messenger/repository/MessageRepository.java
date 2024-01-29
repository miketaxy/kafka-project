package org.trainingkafka.messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trainingkafka.messenger.model.Message;
import org.trainingkafka.messenger.model.User;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesByAuthorAndRecipient(User author, User recipient);
}
