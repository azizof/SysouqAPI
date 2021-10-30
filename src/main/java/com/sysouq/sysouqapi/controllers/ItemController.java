package com.sysouq.sysouqapi.controllers;

import com.sysouq.sysouqapi.dto.ItemDTO;
import com.sysouq.sysouqapi.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/services/api/v1/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Add Item to the Database
     *
     * @param title       the title of the item
     * @param description the description of the item
     * @param userId      the id of the user who added this item
     * @param categoryId  the id of the category to which this item belong
     * @param price       the price of the item
     * @param lat         the latitude of the place
     * @param lon         the longitude of the place
     * @param showNumber  show the user number or not
     * @return item details
     */
    @GetMapping("/add")
    public ItemDTO addItem(String title, String description, Long userId, int categoryId, double price,
                           double lat, double lon, boolean showNumber) {
        return itemService.addItem(title, description, userId, categoryId, price, lat, lon, showNumber);
    }

    /**
     * @param id          the id of the item
     * @param title       the title of the item
     * @param description the description of the item
     * @param price       the price of the item
     * @param showNumber  show the user number or not
     * @param active      show the item or not
     * @return item details
     */
    @GetMapping("/update")
    public ItemDTO updateItem(Long id, String title, String description, double price, boolean showNumber, boolean active) {
        return itemService.updateItem(id, title, description, price, showNumber, active);
    }

    /**
     * Delete the item from database
     *
     * @param id the id of the item which will be deleted
     */
    @GetMapping("/delete")
    public void deleteItem(long id) {
        itemService.deleteItem(id);
    }

    /**
     * get all items in area with the given distance and page number
     *
     * @param lat      the latitude of the place
     * @param lon      the longitude of the place
     * @param distance the distance or radius of the area
     * @param page     the count page
     * @return all items in the area by pages and order as last first
     */
    @GetMapping("/byarea")
    public Page<ItemDTO> getItemsByArea(double lat, double lon, int distance, int page) {
        return itemService.getItemsByArea(lat, lon, distance, page);
    }

    /**
     * get all items in the category with the given area ,distance and page number
     *
     * @param categoryId the id of the category
     * @param lat        the latitude of the place
     * @param lon        the longitude of the place
     * @param distance   the distance or radius of the area
     * @param page       the count page
     * @return all items in the area by pages and order as last first
     */
    @GetMapping("/bycategory")
    public Page<ItemDTO> getItemsByAreaAndCategory(long categoryId, double lat, double lon, int distance, int page) {
        return itemService.getItemsByAreaAndCategory(categoryId, lat, lon, distance, page);
    }

    /**
     * Get the item with the given id
     *
     * @param id the id of the Item
     * @return the dto of the item with the given item
     */
    @GetMapping("/byid")
    public ItemDTO getItemById(Long id) {
        return itemService.getItemById(id);
    }

    /**
     * search by text
     *
     * @param searchText the search text
     * @param lat        the latitude of the place
     * @param lon        the longitude of the place
     * @param distance   the distance or radius of the area
     * @param page       the count page
     * @return all items with the title contain search text
     */
    @GetMapping("/search")
    public Page<ItemDTO> search(String searchText, double lat, double lon, int distance, int page) {
        return itemService.search(searchText, lat, lon, distance, page);
    }


    /**
     * get all items in the Country
     *
     * @param page the count page
     * @return all items
     */
    @GetMapping("/getall")
    public Page<ItemDTO> getAllItems(int page) {
        return itemService.getAllItems(page);
    }

    /**
     * get all items which inserted by the user
     *
     * @param userId the id of the user
     * @return all items which inserted by the user
     */
    @GetMapping("/byuser")
    public Page<ItemDTO> getItemsByUser(Long userId, int page) {
        return itemService.getItemsByUser(userId, page);
    }

    /**
     * Add view to the item
     *
     * @param itemId the item id which will be the view added
     */
    @GetMapping("/addview")
    public void addView(long itemId) {
        itemService.addView(itemId);
    }

}
