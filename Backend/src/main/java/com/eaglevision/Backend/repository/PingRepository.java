package com.eaglevision.Backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.BuyerCheckPing;
import com.eaglevision.Backend.model.Ping;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface PingRepository extends JpaRepository<Ping, Integer> {
	
	public List<Ping> findByItem_itemId(Integer itemId);
	
	public Ping findByItem_itemIdAndPingId(Integer itemId,Integer pingId);
	
	@Query(value ="SELECT bcp.*,p.* from buyer_check_ping bcp LEFT JOIN ping p on bcp.ping_id=p.ping_id LEFT JOIN item i on i.item_id=p.item_id LEFT JOIN shop s ON s.shop_id=i.shop_id LEFT JOIN shop_location l on l.shop_shop_id=s.shop_id WHERE bcp.buyer_id=:buyerId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius)",nativeQuery = true)
	public Page<BuyerCheckPing> findBuyerCheckPingsByBuyer(@Param("buyerId")Integer buyerId,@Param("longitude")Double longitude,@Param("latitude")Double latitude,Double radius,Pageable pageable);
	
	
	@Query(value="SELECT * from buyer_check_ping bcp LEFT JOIN ping p on bcp.ping_id=p.ping_id WHERE bcp.buyer_id=:buyerId",nativeQuery = true)
	public Page<BuyerCheckPing> findBuyerCheckPingsByBuyer(@Param("buyerId") Integer buyerId,Pageable pageable);

	@Query(value="SELECT bcp.*,p.* FROM buyer_check_ping bcp LEFT JOIN vendor_response_ping vrp on vrp.ping_id=bcp.response_id LEFT JOIN ping p ON p.ping_id=vrp.ping_id  WHERE vrp.vendor_id=:vendorId",nativeQuery = true)
	public Page<BuyerCheckPing> findAllRespondedBuyerCheckPingsByVendorId(@Param("vendorId")Integer vendorId, Pageable pageable);

	
	@Query(value="SELECT bcp.*,p.* FROM buyer_check_ping bcp LEFT JOIN vendor_response_ping vrp on vrp.ping_id=bcp.response_id LEFT JOIN ping p ON p.ping_id=vrp.ping_id LEFT JOIN item i on i.item_id=p.item_id LEFT JOIN shop s ON s.shop_id=i.shop_id LEFT JOIN shop_location l on l.shop_shop_id=s.shop_id WHERE vrp.vendor_id=:vendorId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius)",nativeQuery = true)
	public Page<BuyerCheckPing> findAllRespondedBuyerCheckPingsByVendor(@Param("vendorId")Integer vendorId, @Param("longitude")Double lon,@Param("latitude") Double lat,
			@Param("radius")Double radius, Pageable pageable);
}
