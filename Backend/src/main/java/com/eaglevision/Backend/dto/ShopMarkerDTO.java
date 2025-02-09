package com.eaglevision.Backend.dto;

import java.util.Base64;

import com.eaglevision.Backend.model.Shop;

public class ShopMarkerDTO {
	
	private String shopMarkerImage;
	
	private String shopMarkerImageName;
	
	private String shopMarkerImageType;
	
	
	public ShopMarkerDTO() {
		
	}
	
	public ShopMarkerDTO(Shop shop) {
		if(shop.getMarkerImageData()!=null) {
			this.shopMarkerImage=shop.getMarkerImageDataB64();
			this.shopMarkerImageName = shop.getMarkerImageName();
			this.shopMarkerImageType = shop.getMarkerImageType();
		}
	}

	public String getShopMarkerImage() {
		return shopMarkerImage;
	}

	public void setShopMarkerImage(String shopMarkerImage) {
		this.shopMarkerImage = shopMarkerImage;
	}

	public String getShopMarkerImageName() {
		return shopMarkerImageName;
	}

	public void setShopMarkerImageName(String shopMarkerImageName) {
		this.shopMarkerImageName = shopMarkerImageName;
	}

	public String getShopMarkerImageType() {
		return shopMarkerImageType;
	}

	public void setShopMarkerImageType(String shopMarkerImageType) {
		this.shopMarkerImageType = shopMarkerImageType;
	}
	
	

}
