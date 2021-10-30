package com.sysouq.sysouqapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlockListDTO {

    /**
     * The blocked User
     */
    private UserDTO blocked;
}
