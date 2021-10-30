package com.sysouq.sysouqapi.entities.chat;

import com.sysouq.sysouqapi.dto.ConversationDTO;
import com.sysouq.sysouqapi.entities.Item;
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
public class Conversation implements Serializable {

    /**
     * The id of the conversation
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The first user in the Conversation
     */
    @Column(name = "first_id")
    private Long first;

    /**
     * The first user in the Conversation
     */
    @Column(name = "second_id")
    private Long second;

    /**
     * The item which this conversation related to
     */
    @ManyToOne
    private Item item;

    /**
     * The date of starting the conversation
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant date = Instant.now();

    /**
     * Check if first user deleted this conversation
     */
    private boolean deleteFirst = false;

    /**
     * Check if second user deleted this conversation
     */
    private boolean deleteSecond = false;

    public ConversationDTO getConversationDTO() {
        return new ConversationDTO(id, first, second, item.getDto(), date, deleteFirst, deleteSecond);
    }

}
