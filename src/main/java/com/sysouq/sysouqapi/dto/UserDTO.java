package com.sysouq.sysouqapi.dto;

import com.sysouq.sysouqapi.entities.Item;
import com.sysouq.sysouqapi.entities.user.BlockList;
import com.sysouq.sysouqapi.entities.user.FavoriteList;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserDTO {

    /**
     * the id of the User
     */
    private Long id;

    /**
     * The display name
     */
    private String name;

    /**
     * the Email address if the user create account not logged in by facebook or
     * google
     */
    private String email;

    /**
     * the mobile number of the user it could be empty as start
     */
    private String phone;

    /**
     * The facebook id if the user logged in by facebook account
     */
    private String facebookId;

    /**
     * The google id if the user logged in by google account
     */
    private String googleId;

    /**
     * The current ratting of the user values between (0.0 - 5.0)
     */
    private int currentRating;

    /**
     * The Avatar of the user to get his image if this is equal null then
     * generate the Image as the first letter from the user name
     */
    private String avatarUrl;

    /**
     * The list of user items
     */
    private Set<Item> items;

    /**
     * The favorite list
     */
    private FavoriteList favoriteList;

    /**
     * Blocked user list
     */
    private Set<BlockListDTO> blockList;

    /**
     * The date of register
     */
    private Instant date;

    public UserDTO(Long id, String name, String email, String phone, String facebookId, String googleId,
                   int currentRating, String avatarUrl, Set<Item> items, FavoriteList favoriteList, Instant date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.facebookId = facebookId;
        this.googleId = googleId;
        this.currentRating = currentRating;
        this.avatarUrl = avatarUrl;
        this.items = items;
        this.favoriteList = favoriteList;
        this.date = date;
    }
}
