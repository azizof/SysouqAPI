package com.sysouq.sysouqapi.entities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class BlockList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The blocker
     */
    private Long blockerId;

    /**
     * The blocked user
     */
    private Long blockedId;


}
