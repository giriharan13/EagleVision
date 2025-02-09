package com.eaglevision.Backend.dto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.eaglevision.Backend.model.Address;
import com.eaglevision.Backend.model.Hours;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.model.ShopCategory;
import com.eaglevision.Backend.model.ShopLocation;


public class ShopDTO {
    private Integer shopId;

    private String shopName;

    private String description;

    private String contactNumber;

    private Integer vendorId;
    
    private String vendorName;

    private Address address;

    private Hours hours;
    
    private ShopLocation shopLocation;
    
    private ShopCategory shopCategory;

	private String shopImageData;
	
	private String imageName;
	
	private String imageType;
	
	private String markerImageData;
	
	private String markerImageName;
	
	private String markerImageType;

    public ShopDTO(Shop shop) {
        this.shopId = shop.getShopId();
        this.shopName = shop.getShopName();
        this.description = shop.getDescription();
        this.contactNumber = shop.getContactNumber();
        this.vendorId = shop.getVendor().getUserId();
        this.vendorName = shop.getVendor().getUserName();
        this.address = shop.getAddress();
        this.hours = shop.getHours();
        this.shopCategory = shop.getShopCategory();
        this.shopLocation = shop.getShopLocation();
        if(shop.getShopImageData()!=null) this.shopImageData = Base64.getEncoder().encodeToString(shop.getShopImageData()); 
        this.imageName = shop.getImageName();
        this.imageType = shop.getImageType();
        if(shop.getMarkerImageData()!=null) this.markerImageData = Base64.getEncoder().encodeToString(shop.getMarkerImageData());
        this.markerImageName = shop.getMarkerImageName();
        this.markerImageType = shop.getMarkerImageType();
        
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendor(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public Integer getShopId() {
        return shopId;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public ShopLocation getShopLocation() {
		return shopLocation;
	}

	public void setShopLocation(ShopLocation shopLocation) {
		this.shopLocation = shopLocation;
	}

	public ShopCategory getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}

	public String getShopImageData() {
		return shopImageData;
	}

	public void setShopImageData(String shopImageData) {
		this.shopImageData = shopImageData;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getMarkerImageData() {
		return markerImageData;
	}

	public void setMarkerImageData(String markerImageData) {
		this.markerImageData = markerImageData;
	}

	public String getMarkerImageName() {
		return markerImageName;
	}

	public void setMarkerImageName(String markerImageName) {
		this.markerImageName = markerImageName;
	}

	public String getMarkerImageType() {
		return markerImageType;
	}

	public void setMarkerImageType(String markerImageType) {
		this.markerImageType = markerImageType;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	
	
	
    
}
