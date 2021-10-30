package com.sysouq.sysouqapi.repositories;

import com.sysouq.sysouqapi.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findAllByUser_id(Long userId, Pageable pageable);


    @Query(value = "SELECT *,(6371 * acos(cos(radians(?1)) * cos(radians(lat)) * cos(radians(lon) - radians(?2))+sin(radians(?1)) * sin(radians(lat)))) as distance FROM item WHERE active=1 HAVING distance < ?3"
            ,countQuery = "SELECT count(*),(6371 * acos(cos(radians(?1)) * cos(radians(lat)) * cos(radians(lon) - radians(?2))+sin(radians(?1)) * sin(radians(lat)))) as distance FROM item WHERE active=1 HAVING distance < ?3"
            ,nativeQuery = true)
    Page<Item> findAllByArea(double lat, double lon,int distance, Pageable pageable);

    @Query(value = "SELECT *,(6371 * acos(cos(radians(?1)) * cos(radians(lat)) * cos(radians(lon) - radians(?2))+sin(radians(?1)) * sin(radians(lat)))) as distance FROM item WHERE active=1 AND category_id=?4 HAVING distance < ?3"
            ,countQuery = "SELECT count(*),(6371 * acos(cos(radians(?1)) * cos(radians(lat)) * cos(radians(lon) - radians(?2))+sin(radians(?1)) * sin(radians(lat)))) as distance FROM item WHERE active=1 AND category_id=?4 HAVING distance < ?3"
            ,nativeQuery = true)
    Page<Item> findAllByCategory_id(double lat, double lon, int distance, long categoryId, Pageable pageable);

    @Query(value = "SELECT *,(6371 * acos(cos(radians(?1)) * cos(radians(lat)) * cos(radians(lon) - radians(?2))+sin(radians(?1)) * sin(radians(lat)))) as distance FROM item i WHERE i.active=1 AND i.title LIKE %?4% HAVING distance < ?3"
            ,countQuery = "SELECT count (*),(6371 * acos(cos(radians(?1)) * cos(radians(lat)) * cos(radians(lon) - radians(?2))+sin(radians(?1)) * sin(radians(lat)))) as distance FROM item i WHERE i.active=1 AND i.title LIKE %?4% HAVING distance < ?3"
            ,nativeQuery = true)
    Page<Item> search(double lat, double lon, int distance , String searchText, Pageable pageable);



}
