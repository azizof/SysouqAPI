package com.sysouq.sysouqapi.dto;

import com.sysouq.sysouqapi.entities.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class ItemDTO {

    /**
     * The id of the item
     */
    private Long id;

    /**
     * The title of the item
     */
    private String title;

    /**
     * the description
     */
    private String description;

    /**
     * the user who created this item
     */
    private UserDTO user;

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
    private Instant date;

    /**
     * The number of views
     */
    private int views;

    /**
     * the Album of this item photos
     */
    private List<Photo> photos;


}
