package com.eaglevision.Backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);

    Boolean existsByUserName(String userName);
}