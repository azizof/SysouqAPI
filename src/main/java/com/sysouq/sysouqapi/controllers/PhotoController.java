package com.sysouq.sysouqapi.controllers;

import com.sysouq.sysouqapi.entities.Photo;
import com.sysouq.sysouqapi.services.PhotoService;
import com.sysouq.sysouqapi.utils.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/services/api/v1/item/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;


    /**
     * Add images (Photos) to the given item
     *
     * @param itemId the id of the item which has the given photos
     * @param file  the photos which will be uploaded and bind to the given item id
     */
    @PostMapping("/{itemId}/add")
    public void add(@PathVariable("itemId") Long itemId, MultipartFile file) {
       photoService.add(itemId,file);
    }

    /**
     * Delete image (Photo) with the given id
     *
     * @param id the id of the Photo which will be deleted
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        photoService.delete(id);
    }
}