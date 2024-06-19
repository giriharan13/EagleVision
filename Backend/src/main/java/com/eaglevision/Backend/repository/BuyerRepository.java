package com.eaglevision.Backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Integer> {

    Optional<Buyer> findByUserName(String userName);
}
