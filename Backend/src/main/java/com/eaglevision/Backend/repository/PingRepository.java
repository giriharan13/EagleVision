package com.eaglevision.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eaglevision.Backend.model.Ping;

public interface PingRepository extends JpaRepository<Ping, Integer> {
	
	public List<Ping> findByItem_itemId(Integer itemId);
	
	public Ping findByItem_itemIdAndPingId(Integer itemId,Integer pingId);
}
