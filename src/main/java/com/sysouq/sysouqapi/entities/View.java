package com.sysouq.sysouqapi.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class View {

    /**
     * The id of the Item
     */
    @Id
    private Long itemId;

    /**
     * the number of views
     */
    @Column(columnDefinition = "Integer(11) default '1' ")
    private int views;

    public View(Long itemId) {
        this.itemId = itemId;
    }
}
