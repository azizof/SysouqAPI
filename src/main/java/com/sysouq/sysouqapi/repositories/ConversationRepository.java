package com.sysouq.sysouqapi.repositories;

import com.sysouq.sysouqapi.entities.chat.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("select c from Conversation c where c.first =?1 and c.deleteFirst=false " +
            "or c.second= ?1 and c.deleteSecond=false")
    List<Conversation> findAllByUserId(Long userId);

    @Query("select c from Conversation c where (c.first =?1 and c.deleteFirst=false) " +
            "and (c.second= ?2 and c.deleteSecond=false) and c.item.id = ?3")
    Optional<Conversation> getByFirstAndSecondAndItem_Id(Long first, Long second, Long itemId);

}
