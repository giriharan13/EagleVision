package com.eaglevision.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer>{
	
	public List<Shop> findShopsByVendor_userId(Integer userId);
	
	public Shop findShopByVendor_userIdAndShopId(Integer userId,Integer shopId);
	

}
