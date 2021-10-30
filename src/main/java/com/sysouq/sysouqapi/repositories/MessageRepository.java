package com.sysouq.sysouqapi.repositories;

import com.sysouq.sysouqapi.entities.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from Message m where m.conversationId = ?1")
    List<Message> findAllByConversationId(Long conversationId);

    @Query("select m from Message m where m.conversationId = ?1 and m.recipientId = ?2 and m.messageStatus= 'RECEIVED'")
    List<Message> findAllUnRead(Long conversationId,Long userId);
}
