package com.eaglevision.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.EagleEye;

@Repository
public interface EagleEyeRepository extends JpaRepository<EagleEye, Integer> {
	
	public EagleEye findByBuyer_userIdAndItem_itemId(Integer userId,Integer itemId);

}
