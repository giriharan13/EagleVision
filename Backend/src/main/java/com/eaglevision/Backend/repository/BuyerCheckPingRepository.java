package com.eaglevision.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eaglevision.Backend.model.BuyerCheckPing;

public interface BuyerCheckPingRepository extends JpaRepository<BuyerCheckPing, Integer> {

    List<BuyerCheckPing> findByItem_itemId(Integer itemId);
}
