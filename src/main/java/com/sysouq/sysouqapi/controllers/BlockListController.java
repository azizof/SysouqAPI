package com.sysouq.sysouqapi.controllers;

import com.sysouq.sysouqapi.dto.BlockListDTO;
import com.sysouq.sysouqapi.services.BlockListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/services/api/v1/user/block")
public class BlockListController {

    private final BlockListService blockListService;

    @Autowired
    public BlockListController(BlockListService blockListService) {
        this.blockListService = blockListService;
    }


    /**
     * Block the user
     *
     * @param blockerId the id of the user want to block
     * @param blockedId the if of the user get blocked
     */
    @GetMapping("/add")
    public String blockUser(Long blockerId, Long blockedId) {
        blockListService.blockUser(blockerId,blockedId);
        return blockedId + " has blocked " + blockedId;
    }

    /**
     * delete block
     *
     * @param blockerId the id of the user want to block
     * @param blockedId the if of the user get blocked
     */
    @GetMapping("/delete")
    public void deleteBlock(Long blockerId, Long blockedId) {
        blockListService.deleteBlock(blockerId,blockedId);
    }

    /**
     * Get all blocked user by the user with the given id
     *
     * @param blockerId the id of the user
     * @return list of blocked users
     */
    @GetMapping("/list")
    public List<BlockListDTO> getBlockListByUserId(Long blockerId) {
        return blockListService.getBlockListByUserId(blockerId);
    }

}
