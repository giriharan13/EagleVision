package com.eaglevision.Backend.service.bridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.dto.CreateReviewDTO;
import com.eaglevision.Backend.dto.CreateShopReviewDTO;
import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.ItemReview;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.model.ShopReview;
import com.eaglevision.Backend.repository.BuyerRepository;

@Service
public class BuyerReviewService {

	private BuyerRepository buyerRepository;

	@Autowired
	public BuyerReviewService(BuyerRepository buyerRepository) {
		this.buyerRepository = buyerRepository;
	}

	public Buyer getBuyerForReview(Integer buyerId) {
		return buyerRepository.findById(buyerId).get();
	}

	public ShopReview createShopReview(CreateShopReviewDTO createShopReviewRequest, Shop shop) {
		Integer userId = createShopReviewRequest.getUserId();

		Integer stars = createShopReviewRequest.getStars();

		String comment = createShopReviewRequest.getComment();

		Buyer buyer = this.getBuyerForReview(userId);

		ShopReview shopReview = new ShopReview(stars, false, comment, 0, 0, buyer, shop);
		return shopReview;
	}

	public ItemReview createItemReview(CreateReviewDTO createReviewRequest, Item item) {
		Integer stars = createReviewRequest.getStars();

		Integer userId = createReviewRequest.getUserId();

		Buyer buyer = this.getBuyerForReview(userId);

		ItemReview itemReview = new ItemReview(stars, false, item, buyer);
		return itemReview;
	}
}
