package com.sysouq.sysouqapi.repositories;

import com.sysouq.sysouqapi.entities.user.FavoriteList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteListRepository extends JpaRepository<FavoriteList,Long> {

    Optional<FavoriteList> findByUser_Id(Long userId);
}
