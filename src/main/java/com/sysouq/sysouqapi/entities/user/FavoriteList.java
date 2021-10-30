package com.sysouq.sysouqapi.entities.user;


import com.sysouq.sysouqapi.entities.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class FavoriteList implements Serializable {

    /**
     * The favorite list id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The User to which this favorite list related
     */
    @OneToOne
    private User user;

    /**
     * set of items which user added to his favorite list
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "favorite_item" ,joinColumns = @JoinColumn(name = "favorite_id" ,referencedColumnName = "id") ,
                inverseJoinColumns = @JoinColumn(name = "item_id" ,referencedColumnName = "id"))
    private List<Item> items;
}
