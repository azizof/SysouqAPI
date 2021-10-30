package com.sysouq.sysouqapi.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysouq.sysouqapi.dto.UserDTO;
import com.sysouq.sysouqapi.entities.Item;
import com.sysouq.sysouqapi.entities.chat.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"facebookId"}) ,
        @UniqueConstraint(columnNames = {"googleId"}) , @UniqueConstraint(columnNames = {"phone"})} )
public class User implements Serializable {

    /**
     * the id of the User
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The display name
     */
    @Column(nullable = false)
    @Size(max = 40)
    private String name;

    /**
     * the Email address if the user create account not logged in by facebook or
     * google
     */
    @Email
    @Size(max = 40)
    private String email;

    /**
     * the mobile number of the user it could be empty as start
     */
    @Size(max = 40)
    private String phone;

    /**
     * The Password This will not be stored or user just to create user and send
     * it to server
     */
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    @Size(max = 100)
    private String password;

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
    @Column(nullable = false)
    private int currentRating = 5;

    /**
     * The Avatar of the user to get his image if this is equal null then
     * generate the Image as the first letter from the user name
     */
    private String avatarUrl;

    /**
     * The list of user items
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> items;

    /**
     * The favorite list
     */
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private FavoriteList favoriteList;

    /**
     * The date of register
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant date = Instant.now();


    /**
     * Get the full user data DTO
     * @return dto with all user data
     */
    public UserDTO getFullDto(){
        return new UserDTO(id,name,email,phone,facebookId,googleId,currentRating,avatarUrl,items,
                            favoriteList,date);
    }

    /**
     * Get only necessary user data DTO
     * @return dto with necessary user data
     */
    public UserDTO getPartDto(){
        return new UserDTO(id,name,null,phone,null,null,currentRating,avatarUrl
                ,items,null,date);
    }
}
