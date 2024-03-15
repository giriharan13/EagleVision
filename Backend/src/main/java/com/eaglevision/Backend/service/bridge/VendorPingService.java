package com.eaglevision.Backend.service.bridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.repository.VendorRepository;

@Service
public class VendorPingService {
	
	private VendorRepository vendorRepository;
	
	@Autowired
	public VendorPingService(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}
	
	public Vendor getVendorForPing(Integer userId) {
		return this.vendorRepository.findById(userId).get();
	}
}
