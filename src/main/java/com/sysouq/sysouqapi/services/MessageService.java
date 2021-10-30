package com.sysouq.sysouqapi.services;

import com.sysouq.sysouqapi.dto.MessageDTO;
import com.sysouq.sysouqapi.entities.chat.Conversation;
import com.sysouq.sysouqapi.entities.chat.Message;
import com.sysouq.sysouqapi.entities.chat.MessageStatus;
import com.sysouq.sysouqapi.repositories.ConversationRepository;
import com.sysouq.sysouqapi.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;


    public MessageService(MessageRepository messageRepository, ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
    }

    /**
     * Add message to the db
     *
     * @param senderId       the user who send the message
     * @param conversationId the conversation id
     * @param body           the body of the message
     */
    public MessageDTO addMessage(Long senderId, Long recipientId, Long conversationId, String body) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setRecipientId(recipientId);
        message.setBody(body);
        message.setConversationId(conversationId);
        message.setMessageStatus(MessageStatus.RECEIVED);
        message.setDate(Instant.now());
        Message saved = messageRepository.save(message);
        openConversationIfClose(conversationId);
        return saved.getMessageDTO();
    }

    /**
     * if user deleted the conversation reopen it
     *
     * @param conversationId the id of the conversation
     */
    private void openConversationIfClose(Long conversationId) {
        Conversation c = conversationRepository.findById(conversationId).orElse(null);
        if (c.isDeleteFirst() || c.isDeleteSecond()) {
            c.setDeleteFirst(false);
            c.setDeleteSecond(false);
            conversationRepository.save(c);
        }
    }

    /**
     * get all messages in the conversation
     *
     * @param conversationId the conversation of the messages
     * @return list of all messages in the conversation
     */
    public List<MessageDTO> getAllMessagesInConversation(Long conversationId) {
        List<Message> messages = messageRepository.findAllByConversationId(conversationId);
        List<MessageDTO> messageDTOS = new ArrayList<>();
        messages.forEach(message -> messageDTOS.add(message.getMessageDTO()));
        return messageDTOS;
    }
}
