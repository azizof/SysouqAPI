package com.sysouq.sysouqapi.services;

import com.sysouq.sysouqapi.dto.BlockListDTO;
import com.sysouq.sysouqapi.entities.user.BlockList;
import com.sysouq.sysouqapi.repositories.BlockListRepository;
import com.sysouq.sysouqapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
public class BlockListService {


    private final BlockListRepository blockListRepository;
    private final UserRepository userRepository;

    @Autowired
    public BlockListService(BlockListRepository blockListRepository, UserRepository userRepository) {
        this.blockListRepository = blockListRepository;
        this.userRepository = userRepository;
    }

    /**
     * Block the user
     *
     * @param blockerId the id of the user want to block
     * @param blockedId the if of the user get blocked
     */
    public void blockUser(Long blockerId, Long blockedId) {
        if(!blockListRepository.findByBlockerIdAndBlockedId(blockerId,blockedId).isPresent()){
            BlockList blockList = new BlockList();
            blockList.setBlockerId(blockerId);
            blockList.setBlockedId(blockedId);
            blockListRepository.save(blockList);
        }
    }

    /**
     * delete block
     *
     * @param blockerId the id of the user want to block
     * @param blockedId the if of the user get blocked
     */
    public void deleteBlock(Long blockerId, Long blockedId) {
        blockListRepository.deleteBlockListByBlockerIdAndAndBlockerId(blockerId, blockedId);
    }

    /**
     * Get all blocked user by the user with the given id
     *
     * @param blockerId the id of the user
     * @return list of blocked users
     */
    public List<BlockListDTO> getBlockListByUserId(Long blockerId) {
        List<BlockList> blockLists = blockListRepository.findAllByBlockerId(blockerId);
        List<BlockListDTO> blockListDTOS = new ArrayList<>();
        blockLists.forEach(bl -> blockListDTOS.add(new BlockListDTO(userRepository.findById(bl.getBlockedId()).get().getPartDto())));
        return blockListDTOS;
    }


}
