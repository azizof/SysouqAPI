package com.sysouq.sysouqapi.repositories;

import com.sysouq.sysouqapi.entities.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Long> {


    Optional<Token> findByTokenAndEmail(String token, String email);
}
