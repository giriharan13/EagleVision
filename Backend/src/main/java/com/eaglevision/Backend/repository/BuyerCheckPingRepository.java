package com.eaglevision.Backend.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eaglevision.Backend.model.BuyerCheckPing;

import jakarta.transaction.Transactional;

@Transactional
public interface BuyerCheckPingRepository extends JpaRepository<BuyerCheckPing, Integer> {

    Page<BuyerCheckPing> findAllByItem_itemId(Integer itemId,Pageable pageable);
    
    @Query(value="SELECT bcp.*,p.* FROM buyer_check_ping bcp LEFT JOIN ping p ON bcp.ping_id=p.ping_id LEFT JOIN item i on i.item_id=p.item_id LEFT JOIN shop s on i.shop_id=s.shop_id LEFT JOIN shop_location l on s.shop_id=l.shop_shop_id WHERE i.item_id=:itemId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius)",nativeQuery=true)
    Page<BuyerCheckPing> findAllByItem(@Param("itemId") Integer itemId,@Param("longitude")Double longitude,@Param("latitude") Double latitude,@Param("radius") Double radius,Pageable pageable);
    
    @Query("Select p from BuyerCheckPing p where p.item.shop.vendor.userId=:vendorId")
	public Page<BuyerCheckPing> findAllBuyerCheckPingsOnVendor(@Param("vendorId") Integer vendorId,Pageable page);

    @Query(value="Select * from buyer_check_ping bcp  LEFT JOIN ping p ON bcp.ping_id=p.ping_id LEFT JOIN item i on i.item_id=p.item_id LEFT JOIN shop s on i.shop_id=s.shop_id LEFT JOIN shop_location l on s.shop_id=l.shop_shop_id WHERE s.vendor_id=:vendorId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\\\:\\\\:GEOGRAPHY,:radius)",nativeQuery=true)
	public Page<BuyerCheckPing> findAllBuyerCheckPingsOnVendor(@Param("vendorId") Integer vendorId,@Param("longitude") Double longitude,@Param("latitude") Double latitude, Pageable pageable);
}
