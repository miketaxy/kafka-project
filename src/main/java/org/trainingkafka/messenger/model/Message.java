package org.trainingkafka.messenger.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    public Message(String message, User author, User recipient) {
        this.message = message;
        this.author = author;
        this.recipient = recipient;
    }
}
