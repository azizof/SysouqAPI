package com.sysouq.sysouqapi.dto;

import com.sysouq.sysouqapi.entities.chat.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class MessageDTO {


    /**
     * id of the message
     */
    private Long id;

    /**
     * The User who sent the message
     */
    private Long senderId;

    /**
     * The id of the user who received the message
     */
    private Long recipientId;

    /**
     * the id of the Conversation
     */
    private Long conversationId;
    /**
     * The Body of the message
     */
    private String body;

    /**
     * Check if the message is read or not
     */
    private MessageStatus messageStatus;


}
