package com.sysouq.sysouqapi.controllers;

import com.sysouq.sysouqapi.services.FavoriteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/api/v1/favorite_list")
public class FavoriteListController {

    @Autowired
    private FavoriteListService favoriteListService;


    /**
     * Add Item to user favorite list
     *
     * @param userId the id of the user
     * @param itemId the id of the Item
     */
    @GetMapping("/add")
    public void add(Long userId,Long itemId){
        favoriteListService.add(userId, itemId);
    }
    /**
     * remove Item to user favorite list
     *
     * @param userId the id of the user
     * @param itemId the id of the Item
     */
    public void remove(Long userId , Long itemId){
       favoriteListService.remove(userId, itemId);
    }
}
