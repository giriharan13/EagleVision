package com.eaglevision.Backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.dto.UserDTO;
import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.repository.VendorRepository;

@Service
public class VendorService {

	private VendorRepository vendorRepository;
	
	private UtilityService utilityService;

	@Autowired
	public VendorService(VendorRepository vendorRepository,UtilityService utilityService) {
		this.vendorRepository = vendorRepository;
		this.utilityService = utilityService;
	}

	public Vendor createVendor(Vendor vendor) {
		return this.vendorRepository.save(vendor);
	}

	public UserDTO getUserDTOById(Integer vendorId,String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		
		if(roles.contains("VENDOR")) {
			if(userId!=vendorId) return new UserDTO(this.vendorRepository.findById(vendorId).get());
			throw new Exception("Not allowed to fetch other vendors.");
		}
		
		return new UserDTO(this.vendorRepository.findById(vendorId).get());
	}
	
	public Vendor getVendorById(Integer vendorId,String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		
		if(roles.contains("VENDOR")) {
			if(userId==vendorId) return this.vendorRepository.findById(vendorId).get();
			throw new Exception("Not allowed to fetch other vendors.");
		}
		
		return this.vendorRepository.findById(vendorId).get();
	}

	public Vendor getVendorByName(String name) {
		return this.vendorRepository.findByUserName(name).get();
	}

	public Vendor getVendorByPhoneNumber(String phoneNumber) {
		return this.vendorRepository.findByPhoneNumber(phoneNumber).get();
	}

	public List<Vendor> getVendors() {
		return this.vendorRepository.findAll();
	}

	public Vendor updateVendor(Vendor updatedVendor, Integer vendorId,String authHeader) throws Exception {
		Integer userId = utilityService.getUserId(authHeader);
		
		if(userId!=vendorId) {
			throw new Exception("You are not allowed to update this account.");
		}
		
		Vendor vendor = vendorRepository.findById(userId).get();
		vendor.setUserName(updatedVendor.getUserName());
		vendor.setPhoneNumber(updatedVendor.getPhoneNumber());
		vendor.setDateOfBirth(updatedVendor.getDateOfBirth());
		
		return this.vendorRepository.save(vendor);
	}

	public void deleteVendor(Integer vendorId,String authHeader) throws Exception {
		
		Integer userId = utilityService.getUserId(authHeader);
		
		if(userId!=vendorId) {
			throw new Exception("You are not allowed to update this account.");
		}
		
		this.vendorRepository.deleteById(vendorId);
	}

	public Boolean existsByPhoneNumber(String phoneNumber) {
		return this.vendorRepository.existsByPhoneNumber(phoneNumber).get();
	}

	public Boolean existsByTelegramUserId(String telegramUserId) {
		return this.vendorRepository.existsByTelegramUserId(telegramUserId).get();
	}

}
