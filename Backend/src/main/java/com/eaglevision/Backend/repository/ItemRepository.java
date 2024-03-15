package com.eaglevision.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {
	
	public Item findByShop_shopIdAndItemId(Integer shopId,Integer itemId);
	
	List<Item> findByShop_shopId(Integer shopId);
}
