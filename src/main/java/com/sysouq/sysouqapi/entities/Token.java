package com.sysouq.sysouqapi.entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Token implements Serializable {

    /**
     * the id of the token
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * the id of the user
     */
    private String email;

    /**
     * the Token
     */
    private String token;

    /**
     * the time of creating the token
     */
    private LocalDateTime created = LocalDateTime.now();

    /**
     * the time of expire the token after this user cannot user the token again
     */
    private LocalDateTime expire = LocalDateTime.now().plusMinutes(15);
}
