package com.eaglevision.Backend.service.bridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.repository.BuyerRepository;

@Service
public class BuyerPingService {

	private BuyerRepository buyerRepository;

	@Autowired
	public BuyerPingService(BuyerRepository buyerRepository) {
		this.buyerRepository = buyerRepository;
	}

	public Buyer getBuyerForPing(Integer userId) {
		return this.buyerRepository.findById(userId).get();
	}

	public String getBuyerNameForPing(Integer userId) {
		return this.buyerRepository.findById(userId).get().getUserName();
	}
}
