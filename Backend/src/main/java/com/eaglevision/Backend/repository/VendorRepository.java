package com.eaglevision.Backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

    Optional<Vendor> findByUserName(String userName);

    Optional<Vendor> findByPhoneNumber(String phoneNumber);

    Optional<Boolean> existsByPhoneNumber(String phoneNumber);

    Optional<Boolean> existsByTelegramUserId(String telegramUserId);
}
