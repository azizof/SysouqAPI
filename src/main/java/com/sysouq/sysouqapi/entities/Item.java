package com.sysouq.sysouqapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sysouq.sysouqapi.dto.ItemDTO;
import com.sysouq.sysouqapi.entities.chat.Conversation;
import com.sysouq.sysouqapi.entities.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor

public class Item implements Serializable {

    /**
     * The id of the item
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the item
     */
    private String title;

    /**
     * the description
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * the user who created this item
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * the category to which this item belong
     */
    private long categoryId;

    /**
     * the price of the item
     */
    private double price;

    /**
     * the latitude of the place
     */
    private double lat;
    /**
     * the longitude of the place
     */
    private double lon;

    /**
     * should this item be shown or not
     */
    private boolean active;

    /**
     * show the number of the user so the other user can see it and make calls
     */
    private boolean showNumber;

    /**
     * The date of creating this item
     */
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private Instant date;

    /**
     * The number of views
     */
    @Transient
    private int views;

    /**
     * The Conversations
     */
    @JsonIgnore
    @OneToMany
    private List<Conversation> conversations ;

    /**
     * the Album of this item photos
     */
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Photo> photos = new ArrayList<>();

    public ItemDTO getDto(){
        return new ItemDTO(id,title,description,user.getPartDto(),categoryId,price,lat,lon,active,
                showNumber,date,views,photos);
    }
}
