package com.eaglevision.Backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {
	
}
