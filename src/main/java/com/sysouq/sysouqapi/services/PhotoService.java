package com.sysouq.sysouqapi.services;

import com.sysouq.sysouqapi.entities.Item;
import com.sysouq.sysouqapi.entities.Photo;
import com.sysouq.sysouqapi.repositories.ItemRepository;
import com.sysouq.sysouqapi.repositories.PhotoRepository;
import com.sysouq.sysouqapi.utils.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository, ItemRepository itemRepository) {
        this.photoRepository = photoRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Add images (Photos) to the given item
     *
     * @param itemId the id of the item which has the given photos
     * @param file   the photos which will be uploaded and bind to the given item id
     */
    public void add(Long itemId, MultipartFile file) {
        Photo photo = new Photo();
        Item item = itemRepository.findById(itemId).get();
        photo.setItem(item);
        photo.setUrl(ImageUploadUtil.uploadItemPhotos(itemId, file));
        item.getPhotos().add(photoRepository.save(photo));
        itemRepository.save(item);

    }

    /**
     * Delete image (Photo) with the given id
     *
     * @param id the id of the Photo which will be deleted
     */
    public void delete(Long id) {
        photoRepository.deleteById(id);
    }
}
