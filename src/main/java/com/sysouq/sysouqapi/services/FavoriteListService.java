package com.sysouq.sysouqapi.services;

import com.sysouq.sysouqapi.entities.Item;
import com.sysouq.sysouqapi.entities.user.FavoriteList;
import com.sysouq.sysouqapi.repositories.FavoriteListRepository;
import com.sysouq.sysouqapi.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteListService {

    private final FavoriteListRepository favoriteListRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public FavoriteListService(FavoriteListRepository favoriteListRepository, ItemRepository itemRepository) {
        this.favoriteListRepository = favoriteListRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Add Item to user favorite list
     *
     * @param userId the id of the user
     * @param itemId the id of the Item
     */
    public void add(Long userId, Long itemId) {
        FavoriteList favoriteList = favoriteListRepository.findByUser_Id(userId).get();
        favoriteList.getItems().add(itemRepository.findById(itemId).get());
        favoriteListRepository.save(favoriteList);
    }

    /**
     * remove Item to user favorite list
     *
     * @param userId the id of the user
     * @param itemId the id of the Item
     */
    public void remove(Long userId , Long itemId){
        FavoriteList favoriteList = favoriteListRepository.findByUser_Id(userId).get();
        Item item = itemRepository.findById(itemId).get();
        if(favoriteList.getItems().contains(item)){
            favoriteList.getItems().remove(item);
        }
        favoriteListRepository.save(favoriteList);
    }

}
