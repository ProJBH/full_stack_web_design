package com.bohuajia.o2o.dao;

import com.bohuajia.o2o.entity.Shop;

public interface ShopDAO {
	/**
	 * Add new Shop
	 * 
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/**
	 * Update shop
	 * 
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
	/**
	 * Search shop by shopId
	 * 
	 * @param shopId
	 * @return shop
	 */
	Shop queryByShopId(long shopId);
}
