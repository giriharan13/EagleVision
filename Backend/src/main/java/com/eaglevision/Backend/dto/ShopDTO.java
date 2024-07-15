package com.eaglevision.Backend.dto;

import java.util.ArrayList;
import java.util.List;

import com.eaglevision.Backend.model.Address;
import com.eaglevision.Backend.model.Hours;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Shop;

public class ShopDTO {
    private Integer shopId;

    private String shopName;

    private String description;

    private String contactNumber;

    private Integer vendorId;

    private List<ShopReviewDTO> shopReviews = new ArrayList<ShopReviewDTO>();

    private List<Item> items = new ArrayList<Item>();

    private Address address;

    private Hours hours;

    public ShopDTO(Shop shop) {
        this.shopId = shop.getShopId();
        this.shopName = shop.getShopName();
        this.description = shop.getDescription();
        this.contactNumber = shop.getContactNumber();
        this.vendorId = shop.getVendor().getUserId();
        this.items = shop.getItems();
        this.address = shop.getAddress();
        this.hours = shop.getHours();
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

    public List<ShopReviewDTO> getShopReviews() {
        return shopReviews;
    }

    public void setShopReviews(List<ShopReviewDTO> shopReviews) {
        this.shopReviews = shopReviews;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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

    public void addShopReview(ShopReviewDTO shopReview) {
        this.shopReviews.add(shopReview);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
