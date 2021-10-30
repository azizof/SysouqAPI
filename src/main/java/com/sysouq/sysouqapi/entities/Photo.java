package com.sysouq.sysouqapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Photo implements Serializable {

    /**
     * The id of this image
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The Url of the image
     */
    private String url;


    /**
     * The album of the image
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
}
