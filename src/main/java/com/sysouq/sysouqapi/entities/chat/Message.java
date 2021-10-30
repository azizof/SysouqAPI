package com.sysouq.sysouqapi.entities.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sysouq.sysouqapi.dto.MessageDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Message implements Serializable {

    /**
     * id of the message
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The id of the User who sent the message
     */
    private Long senderId;

    /**
     * The id of the user who received the message
     */
    private Long recipientId;

    /**
     * The conversation which content this message
     */
    private Long conversationId;

    /**
     * The Body of the message
     */
    @Column(columnDefinition = "TEXT")
    private String body;

    /**
     * The date of sent the message
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant date;

    /**
     * Check if the message is read or not
     */
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;


    public MessageDTO getMessageDTO() {
        return new MessageDTO(id, senderId, recipientId, conversationId, body, messageStatus);
    }

}
