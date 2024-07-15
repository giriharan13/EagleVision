package com.eaglevision.Backend.dto;

import java.util.ArrayList;
import java.util.List;

import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.ItemReview;
import com.eaglevision.Backend.model.Ping;

public class ItemDTO {
    private Integer itemId;

    private String itemName, itemDescription;

    private Double itemPrice;

    private Integer vendorId;

    private List<ItemReviewDTO> itemReviews = new ArrayList<>();

    private List<Ping> pingHistory;

    public ItemDTO(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.itemDescription = item.getItemDescription();
        this.itemPrice = item.getItemPrice();
        this.vendorId = item.getShop().getVendor().getUserId();
        this.itemReviews = item.getItemReviews().stream().map((ItemReview itemReview) -> new ItemReviewDTO(itemReview))
                .toList();
        this.pingHistory = item.getPingHistory();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public List<ItemReviewDTO> getItemReviews() {
        return itemReviews;
    }

    public void setItemReviews(List<ItemReviewDTO> itemReviews) {
        this.itemReviews = itemReviews;
    }

    public List<Ping> getPingHistory() {
        return pingHistory;
    }

    public void setPingHistory(List<Ping> pingHistory) {
        this.pingHistory = pingHistory;
    }

}
