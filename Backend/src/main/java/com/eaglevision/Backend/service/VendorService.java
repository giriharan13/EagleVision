package com.eaglevision.Backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.repository.VendorRepository;

@Service
public class VendorService {

	private VendorRepository vendorRepository;

	@Autowired
	public VendorService(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}

	public Vendor createVendor(Vendor vendor) {
		return this.vendorRepository.save(vendor);
	}

	public Vendor getVendorById(Integer id) {
		return this.vendorRepository.findById(id).get();
	}

	public List<Vendor> getVendors() {
		return this.vendorRepository.findAll();
	}

	public Vendor updateVendor(Vendor updatedVendor, Integer userId) {
		Vendor vendor = vendorRepository.findById(userId).get();
		vendor.setUserName(updatedVendor.getUserName());
		vendor.setPhoneNumber(updatedVendor.getPhoneNumber());
		vendor.setDateOfBirth(updatedVendor.getDateOfBirth());
		return this.vendorRepository.save(vendor);
	}

	public void deleteVendor(Integer id) {
		this.vendorRepository.deleteById(id);
	}

}
