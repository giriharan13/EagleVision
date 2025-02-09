package com.eaglevision.Backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.dto.BuyerCheckPingDTO;
import com.eaglevision.Backend.dto.CreatePingDTO;
import com.eaglevision.Backend.dto.PingDTO;
import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.BuyerCheckPing;
import com.eaglevision.Backend.model.EagleEye;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Notification;
import com.eaglevision.Backend.model.Ping;
import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.model.VendorResponsePing;
import com.eaglevision.Backend.repository.BuyerCheckPingRepository;
import com.eaglevision.Backend.repository.PingRepository;

import jakarta.transaction.Transactional;

@Service
public class PingService {

	private PingRepository pingRepository;

	private ItemService itemService;

	private VendorService vendorService;
	
	private BuyerService buyerService;
	
	private UtilityService utilityService;
	
	private ShopService shopService;

	private BuyerCheckPingRepository buyerCheckPingRepository;
	
	private NotificationService notificationService;

	@Autowired
	public PingService(PingRepository pingRepository, ItemService itemService, VendorService vendorService,BuyerService buyerService,
			BuyerCheckPingRepository buyerCheckPingRepository,ShopService shopService,UtilityService utilityService,NotificationService notificationService) {
		this.pingRepository = pingRepository;
		this.itemService = itemService;
		this.vendorService = vendorService;
		this.buyerService = buyerService;
		this.buyerCheckPingRepository = buyerCheckPingRepository;
		this.shopService = shopService;
		this.utilityService = utilityService;
		this.notificationService = notificationService;
	}

	public Ping createPing(Item item, CreatePingDTO createPingRequest) {
		if (createPingRequest.getType() == 0) {
			Buyer buyer = createPingRequest.getBuyer();
			BuyerCheckPing buyerCheckPing = new BuyerCheckPing(buyer, item);
			return this.pingRepository.save(buyerCheckPing);
		} else {
			Vendor vendor = createPingRequest.getVendor();
			VendorResponsePing vendorResponsePing = new VendorResponsePing(createPingRequest.getQuantity(), item,
					vendor);
			return this.pingRepository.save(vendorResponsePing);
		}
	}

	public List<Ping> getAllPings(Integer itemId) {
		return this.pingRepository.findByItem_itemId(itemId);
	} 

	public Ping getPing(Integer itemId, Integer pingId) {
		return this.pingRepository.findByItem_itemIdAndPingId(itemId, pingId);
	}

	@Transactional
	public VendorResponsePing createVendorResponsePing(Integer shopId, Integer itemId,Integer pingId,CreatePingDTO createPingDTO,String authHeader) throws Exception { //rework

			String roles = utilityService.getRoles(authHeader);
			Integer ownerId = shopService.getOwnerId(shopId);
			Integer userId = utilityService.getUserId(authHeader);
			
			if(!roles.contains("VENDOR") || !userId.equals(ownerId)) {
				throw new Exception("Not allowed to create response pings.");
			}
			
			Item item = itemService.getItem(shopId, itemId,authHeader);
			Vendor vendor = vendorService.getVendorById(createPingDTO.getUserId(),authHeader);
			VendorResponsePing vendorResponsePing = new VendorResponsePing(createPingDTO.getQuantity(),
					item, vendor);
			BuyerCheckPing buyerCheckPing = (BuyerCheckPing) pingRepository.findById(pingId).orElse(null);
			
			if(buyerCheckPing==null) {
				throw new Exception("No such ping with the given ping id exists.");
			}
			
			buyerCheckPing.setVendorResponsePing(vendorResponsePing);
			pingRepository.save(vendorResponsePing);
			pingRepository.save(buyerCheckPing);
			
			Notification notification = new Notification();
			notification.setTitle("Vendor Response Ping");
			notification.setContent(vendor.getUserName()+" has responded to your ping on item "+item.getItemName());
			notification.setSentTo(buyerCheckPing.getBuyer());
			buyerCheckPing.getBuyer().addNotification(notification);
			
			
			this.sendNotificationsToEagleEyes(item);
			
			this.notificationService.create(notification);
			this.notificationService.sendNotificationToUser(buyerCheckPing.getBuyer().getUserName(),notification);
			
			return vendorResponsePing;
	}
	
	@Async
	private void sendNotificationsToEagleEyes(Item item) {
		
		for(EagleEye eagleEye:item.getEagleEyes()) {
			Notification notification = new Notification();
			notification.setTitle("Eagle Eye Intel");
			notification.setContent(item.getShop().getVendor().getUserName()+" has responded to a ping on item "+item.getItemName());
			notification.setSentTo(eagleEye.getBuyer());
			eagleEye.getBuyer().addNotification(notification);
			
			
			this.notificationService.create(notification);
			this.notificationService.sendNotificationToUser(eagleEye.getBuyer().getUserName(),notification);
		}
		
	}

	public Page<PingDTO> getAllPingsOnVendor(Integer vendorId,Double lat,Double lon,Integer pageNumber,Integer pageSize,String authHeader) throws Exception{
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		
		Pageable pageable;
		
		if(pageSize==0) {
			pageable = Pageable.unpaged();
		}
		else {
			pageable = PageRequest.of(pageNumber, pageSize);
		}
		
		if(roles.contains("VENDOR")) {
			if(userId==vendorId) return this.buyerCheckPingRepository.findAllBuyerCheckPingsOnVendor(vendorId, pageable).map((BuyerCheckPing buyerCheckPing) -> new PingDTO(buyerCheckPing));
			throw new Exception("Not allowed to fetch pings from other vendors.");
		}
		
		
		return this.buyerCheckPingRepository.findAllBuyerCheckPingsOnVendor(vendorId, lat,lon,pageable).map((BuyerCheckPing buyerCheckPing) -> new PingDTO(buyerCheckPing));
	}

//	public VendorResponsePing createVendorResponsePing(Integer pingId, CreatePingDTO createPingDTO) {
//		BuyerCheckPing buyerCheckPing = buyerCheckPingRepository.findById(pingId).get();
//		Vendor vendor = vendorService.getVendorById(createPingDTO.getUserId());
//		VendorResponsePing vendorResponsePing = new VendorResponsePing(createPingDTO.getQuantity(),buyerCheckPing.getItem(),vendor);
//		buyerCheckPing.setVendorResponsePing(vendorResponsePing);
//		pingRepository.save(vendorResponsePing);
//		pingRepository.save(buyerCheckPing);
//		return vendorResponsePing;
//		
//	}

	public Page<BuyerCheckPingDTO> getAllBuyerCheckPings(Integer shopId,Integer itemId, Double lat, Double lon,
			Integer pageNumber, Integer pageSize,String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Integer ownerId = shopService.getOwnerId(shopId);
		Integer userId = utilityService.getUserId(authHeader);
		Double radius = utilityService.getRadius(roles);
		
		Pageable pageable;
		
		if(pageSize==0) {
			pageable = Pageable.unpaged();
		}
		else {
			pageable = PageRequest.of(pageNumber, pageSize);
		}
		
		if(roles.contains("VENDOR")) {
			if(userId.equals(ownerId)) return this.buyerCheckPingRepository.findAllByItem_itemId(itemId,pageable).map((BuyerCheckPing buyerCheckPing) -> new BuyerCheckPingDTO(buyerCheckPing));
			throw new Exception("Not allowed to fetch pings from this item.");
		}		
		
		return this.buyerCheckPingRepository.findAllByItem(itemId,lon,lat,radius,pageable).map((BuyerCheckPing buyerCheckPing) -> new BuyerCheckPingDTO(buyerCheckPing));
	}

	public BuyerCheckPingDTO createBuyerCheckPing(Integer shopId, Integer itemId, CreatePingDTO createPingDTO,Double lat,Double lon, String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Not allowed to create Buyer check pings.");
		}
		
		Item item = itemService.getItem(shopId, itemId, lat, lon, authHeader);
		
		if(item==null) {
			throw new Exception("No such item/shop exists.");
		}
		
		Buyer buyer = this.buyerService.getBuyerById(createPingDTO.getUserId(), authHeader);
		BuyerCheckPing buyerCheckPing = new BuyerCheckPing(buyer, item);
		BuyerCheckPingDTO buyerCheckPingDTO = new BuyerCheckPingDTO(this.pingRepository.save(buyerCheckPing));
		
		
		Notification notification = new Notification();
		notification.setTitle("Buyer Check Ping");
		notification.setContent(buyer.getUserName()+" has pinged for the item "+item.getItemName());
		Vendor vendor = item.getShop().getVendor();
		notification.setSentTo(vendor);
		item.getShop().getVendor().addNotification(notification);
		
		this.notificationService.create(notification);
		this.notificationService.sendNotificationToUser(vendor.getUserName(),notification);
		if(vendor.getOptedForTelegramNotifications()) {
			notificationService.sendTelegramNotificationToVendor(notification);
		}
		
		return buyerCheckPingDTO;
	}

	public Page<BuyerCheckPingDTO> getAllPingsByBuyer(Integer buyerId, Double lat, Double lon, Integer pageNumber, Integer pageSize,
			String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		Double radius = utilityService.getRadius(roles);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Not allowed to get pings by buyers.");
		}
		
		Pageable pageable;
		
		if(pageSize==0) {
			pageable = Pageable.unpaged();
		}
		else {
			pageable = PageRequest.of(pageNumber,pageSize);
		}
		
		if(buyerId.equals(userId)) {
			Page<BuyerCheckPing> buyerCheckPings = this.pingRepository.findBuyerCheckPingsByBuyer(buyerId, pageable);
			return buyerCheckPings.map((BuyerCheckPing buyerCheckPing)->new BuyerCheckPingDTO(buyerCheckPing));
		}
		Page<BuyerCheckPing> buyerCheckPings = this.pingRepository.findBuyerCheckPingsByBuyer(buyerId,lon,lat,radius, pageable);
		return buyerCheckPings.map((BuyerCheckPing buyerCheckPing)->new BuyerCheckPingDTO(buyerCheckPing));
	}

	public Page<BuyerCheckPingDTO> getAllResponsePingsByVendor(Integer vendorId, Double lat, Double lon, Integer pageNumber,
			Integer pageSize, String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		Double radius = utilityService.getRadius(roles);
		
		if(roles.contains("VENDOR") && !userId.equals(vendorId)) {
			throw new Exception("Not allowed to get pings by buyers.");
		}
		

		Pageable pageable;
		
		if(pageSize==0) {
			pageable = Pageable.unpaged();
		}
		else {
			pageable = PageRequest.of(pageNumber, pageSize);
		}
		
		if(vendorId.equals(userId)) {
			Page<BuyerCheckPing> buyerCheckPings = this.pingRepository.findAllRespondedBuyerCheckPingsByVendorId(vendorId,pageable);
			return buyerCheckPings.map((BuyerCheckPing buyerCheckPing)->new BuyerCheckPingDTO(buyerCheckPing));
		}
		
		Page<BuyerCheckPing> buyerCheckPings = this.pingRepository.findAllRespondedBuyerCheckPingsByVendor(vendorId,lon,lat,radius,pageable);
		
		return buyerCheckPings.map((BuyerCheckPing buyerCheckPing)->new BuyerCheckPingDTO(buyerCheckPing));
	}
}
