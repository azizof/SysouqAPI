package com.sysouq.sysouqapi.services;

import com.sysouq.sysouqapi.dto.ConversationDTO;
import com.sysouq.sysouqapi.entities.chat.Conversation;
import com.sysouq.sysouqapi.entities.chat.Message;
import com.sysouq.sysouqapi.entities.chat.MessageStatus;
import com.sysouq.sysouqapi.repositories.ConversationRepository;
import com.sysouq.sysouqapi.repositories.ItemRepository;
import com.sysouq.sysouqapi.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository, MessageRepository messageRepository, ItemRepository itemRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Add new Conversation
     *
     * @param firstId  the id of the first user
     * @param secondId the id of the second user
     * @param itemId   the id of the item
     */
    public ConversationDTO startConversation(Long firstId, Long secondId, Long itemId) {
        if (conversationRepository.getByFirstAndSecondAndItem_Id(firstId, secondId, itemId).isPresent()) {
            return conversationRepository.getByFirstAndSecondAndItem_Id(firstId, secondId, itemId).get().getConversationDTO();
        } else {
            Conversation conversation = new Conversation();
            conversation.setFirst(firstId);
            conversation.setSecond(secondId);
            conversation.setItem(itemRepository.findById(itemId).get());
            conversation.setDate(Instant.now());
            return conversationRepository.save(conversation).getConversationDTO();
        }

    }

    /**
     * Delete the conversation by users
     *
     * @param conversationId the id of the conversation
     * @param userId         the user id who want to delete this conversation
     */
    public void delete(Long conversationId, Long userId) {
        Conversation conversation = conversationRepository.findById(conversationId).get();
        if (conversation.getFirst() == userId) {
            conversation.setDeleteFirst(true);
        } else {
            conversation.setDeleteSecond(true);
        }
        conversationRepository.save(conversation);
    }

    /**
     * Get all user conversations
     *
     * @param userId the id of the user
     * @return set of all user conversations
     */
    public List<ConversationDTO> getAllUserConversations(Long userId) {
        List<ConversationDTO> conversationDTOS = new ArrayList<>();
        List<Conversation> conversations = conversationRepository.findAllByUserId(userId);
        conversations.forEach(conversation -> {
            ConversationDTO conversationDTO = conversation.getConversationDTO();
            conversationDTO.setUnreadMessage(getUnreadMessages(conversation.getId(), userId));
            conversationDTOS.add(conversationDTO);
        });
        return conversationDTOS;
    }

    /**
     * set all messages in conversation to read
     *
     * @param conversationId the conversationId of the conversation
     */
    public void setAllMessagesToRead(Long conversationId,Long userId) {
        List<Message> messages = messageRepository.findAllUnRead(conversationId,userId);
        messages.forEach(msg -> {
            msg.setMessageStatus(MessageStatus.READ);
            messageRepository.save(msg);
        });
    }

    /**
     * get conversation by the given id
     *
     * @param id the id of the conversation
     * @return conversation with the given id
     */
    public ConversationDTO getConversationById(Long id) {
        return conversationRepository.findById(id).get().getConversationDTO();
    }

    /**
     * get all unread messages
     *
     * @param conversationId the id of the conversation
     * @param userId         the user
     * @return
     */
    private int getUnreadMessages(Long conversationId, Long userId) {
        return messageRepository.findAllUnRead(conversationId, userId).size();
    }
}
