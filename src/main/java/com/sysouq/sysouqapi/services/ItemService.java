package com.sysouq.sysouqapi.services;

import com.sysouq.sysouqapi.dto.ItemDTO;
import com.sysouq.sysouqapi.entities.Item;
import com.sysouq.sysouqapi.entities.View;
import com.sysouq.sysouqapi.repositories.ItemRepository;
import com.sysouq.sysouqapi.repositories.UserRepository;
import com.sysouq.sysouqapi.repositories.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.function.Function;

@Service
public class ItemService {

    /**
     * The default size of page while user page request
     */
    private static final int DEFAULT_PAGE_SIZE = 30;

    private final ItemRepository itemRepository;
    private final ViewRepository viewRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository,
                       ViewRepository viewRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.viewRepository = viewRepository;
        this.userRepository = userRepository;
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
    public ItemDTO addItem(String title, String description, Long userId, int categoryId, double price,
                           double lat, double lon, boolean showNumber) {
        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        item.setUser(userRepository.findById(userId).orElse(null));
        item.setCategoryId(categoryId);
        item.setLat(lat);
        item.setLon(lon);
        item.setPrice(price);
        item.setShowNumber(showNumber);
        item.setActive(true);
        item.setDate(Instant.now());
        return itemRepository.save(item).getDto();
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
    public ItemDTO updateItem(Long id, String title, String description, double price, boolean showNumber, boolean active) {
        Item i = itemRepository.findById(id).orElse(null);
        if (i != null) {
            i.setTitle(title);
            i.setPrice(price);
            i.setDescription(description);
            i.setShowNumber(showNumber);
            i.setActive(active);
            return itemRepository.save(i).getDto();
        } else return null;
    }

    /**
     * Delete the item from database
     *
     * @param id the id of the item which will be deleted
     */
    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }

    /**
     * Get the item with the given id
     *
     * @param id the id of the Item
     * @return the dto of the item with the given item
     */
    public ItemDTO getItemById(Long id) {
        return itemRepository.findById(id).orElse(null).getDto();
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
    public Page<ItemDTO> getItemsByArea(double lat, double lon, int distance, int page) {
        Page<Item> items = itemRepository.findAllByArea(lat, lon, distance,
                PageRequest.of(page, DEFAULT_PAGE_SIZE, Sort.by("date").descending()));
        return addViewAndConvertToDto(items);
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
    public Page<ItemDTO> getItemsByAreaAndCategory(long categoryId, double lat, double lon, int distance, int page) {
        Page<Item> items = itemRepository.findAllByCategory_id(lat, lon, distance, categoryId,
                PageRequest.of(page, DEFAULT_PAGE_SIZE, Sort.by("date").descending()));
        return addViewAndConvertToDto(items);
    }

    /**
     * get all items by search text
     *
     * @param searchText the Text of the title
     * @param lat        the latitude of the place
     * @param lon        the longitude of the place
     * @param distance   the distance or radius of the area
     * @param page       the count page
     * @return all items with the title of search text
     */
    public Page<ItemDTO> search(String searchText, double lat, double lon, int distance, int page) {
        Page<Item> items = itemRepository.search(lat, lon, distance, searchText,
                PageRequest.of(page, DEFAULT_PAGE_SIZE, Sort.by("date").descending()));
        return addViewAndConvertToDto(items);
    }


    /**
     * get all items in the Country
     *
     * @param page the count page
     * @return all items
     */
    public Page<ItemDTO> getAllItems(int page) {
        return addViewAndConvertToDto(itemRepository.findAll(PageRequest.of(page, DEFAULT_PAGE_SIZE, Sort.by("date").descending())));
    }

    /**
     * get all items which inserted by the user
     *
     * @param userId the id of the user
     * @return all items which inserted by the user
     */
    public Page<ItemDTO> getItemsByUser(Long userId, int page) {
        Page<Item> items = itemRepository.findAllByUser_id(userId, PageRequest.of(page, DEFAULT_PAGE_SIZE, Sort.by("date").descending()));
        return addViewAndConvertToDto(items);
    }

    /**
     * Set the views for the items
     *
     * @param items the items
     * @return page with items
     */
    private Page<ItemDTO> addViewAndConvertToDto(Page<Item> items) {
        View view = new View();
        view.setViews(0);
        items.forEach(item -> item.setViews(viewRepository.findById(item.getId()).orElse(view).getViews()));
        Page<ItemDTO> itemDAOS = items.map(new Function<Item, ItemDTO>() {

            @Override
            public ItemDTO apply(Item item) {
                return item.getDto();
            }
        });

        return itemDAOS;
    }

    /**
     * Add view to the item
     *
     * @param itemId the item id which will be the view added
     */
    public void addView(long itemId) {
        if (!viewRepository.findById(itemId).isPresent()) {
            viewRepository.save(new View(itemId));
        } else {
            View view = viewRepository.findById(itemId).get();
            view.setViews(view.getViews() + 1);
            viewRepository.save(view);
        }
    }

}
