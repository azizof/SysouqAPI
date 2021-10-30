package com.sysouq.sysouqapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ConversationDTO {

    /**
     * The id of the conversation
     */
    private Long id;

    /**
     * The first user in the Conversation
     */
    private Long first;

    /**
     * The second user in the Conversation
     */
    private Long second;

    /**
     * The item which this conversation related to
     */
    private ItemDTO item;

    /**
     * The date of starting the conversation
     */
    private Instant date;

    /**
     * Check if first user deleted this conversation
     */
    private boolean deleteFirst;

    /**
     * Check if second user deleted this conversation
     */
    private boolean deleteSecond;

    /**
     * number of message that are unread
     */
    private int unreadMessage;

    public ConversationDTO(Long id, Long first, Long second, ItemDTO item, Instant date, boolean deleteFirst, boolean deleteSecond) {
        this.id = id;
        this.first = first;
        this.second = second;
        this.item = item;
        this.date = date;
        this.deleteFirst = deleteFirst;
        this.deleteSecond = deleteSecond;
    }
}
