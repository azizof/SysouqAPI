package com.sysouq.sysouqapi.repositories;

import com.sysouq.sysouqapi.entities.user.BlockList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BlockListRepository extends JpaRepository<BlockList, Long> {

    @Transactional
    @Modifying
    @Query(value ="DELETE FROM `block_list` WHERE `blocker_id`=?1 AND `blocked_id`=?2 " ,nativeQuery = true)
    void deleteBlockListByBlockerIdAndAndBlockerId(Long blockerId, Long blockedId);

    List<BlockList> findAllByBlockerId(Long blockerId);

    Optional<BlockList> findByBlockerIdAndBlockedId(Long blockerId,Long blockedId);
}
