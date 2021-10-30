package com.sysouq.sysouqapi.controllers;

import com.sysouq.sysouqapi.dto.ConversationDTO;
import com.sysouq.sysouqapi.entities.chat.Message;
import com.sysouq.sysouqapi.entities.chat.MessageStatus;
import com.sysouq.sysouqapi.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/services/api/v1/conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    /**
     * Add new Conversation
     *
     * @param firstId  the id of the first user
     * @param secondId the id of the second user
     * @param itemId   the id of the item
     */
    @GetMapping("/add")
    public ConversationDTO startConversation(Long firstId, Long secondId, Long itemId) {
        return conversationService.startConversation(firstId, secondId, itemId);
    }

    /**
     * Delete the conversation by users
     *
     * @param conversationId the id of the conversation
     * @param userId         the user id who want to delete this conversation
     */
    @GetMapping("/delete")
    public void delete(Long conversationId, Long userId) {
        conversationService.delete(conversationId, userId);
    }


    /**
     * Get all user conversations
     *
     * @param userId the id of the user
     * @return set of all user conversations
     */
    @GetMapping("/byuser")
    public List<ConversationDTO> getAllUserConversations(Long userId) {
        return conversationService.getAllUserConversations(userId);
    }


    /**
     * set all messages in conversation to read
     *
     * @param id the id of the conversation
     */
    @GetMapping("/allread")
    public void setAllMessagesToRead(Long id,Long userId) {
        conversationService.setAllMessagesToRead(id,userId);
    }

    /**
     * get conversation by the given id
     *
     * @param id the id of the conversation
     * @return conversation with the given id
     */
    @GetMapping("/byid")
    public ConversationDTO getConversationById(Long id) {
        return conversationService.getConversationById(id);
    }
}