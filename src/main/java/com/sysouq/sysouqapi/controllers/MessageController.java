package com.sysouq.sysouqapi.controllers;


import com.sysouq.sysouqapi.dto.MessageDTO;
import com.sysouq.sysouqapi.entities.chat.Message;
import com.sysouq.sysouqapi.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/services/api/v1/messages")
public class MessageController {


    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    /**
     * get all messages in the conversation
     *
     * @param conversationId the conversation of the messages
     * @return list of all messages in the conversation
     */
    @GetMapping("/getbyconversation")
    public List<MessageDTO> getAllMessagesInConversation(Long conversationId) {
        return messageService.getAllMessagesInConversation(conversationId);
    }
}
