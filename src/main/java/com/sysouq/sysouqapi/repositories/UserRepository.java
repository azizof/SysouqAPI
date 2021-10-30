package com.sysouq.sysouqapi.repositories;

import com.sysouq.sysouqapi.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public Optional<User> findUserByEmail(String email);
    public Optional<User> findUserByFacebookId(String facebookId);
    public Optional<User> findUserByGoogleId(String googleId);


}
