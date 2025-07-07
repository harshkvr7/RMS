package com.harsh.rms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harsh.rms.models.User;

@Repository
public interface  UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
    
    User findByEmail(String email);

}
