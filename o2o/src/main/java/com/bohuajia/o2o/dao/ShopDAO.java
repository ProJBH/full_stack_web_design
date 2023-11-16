package com.bohuajia.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	
	/**
	 * To query shops in pages, 
	 * the conditions that can be entered are: 
	 * shop name (fuzzy), shop status, shop category, region ID, owner
	 * 
	 * @param shopCondition
	 * @param rowIndex
	 *            From which row should start fetching data
	 * @param pageSize
	 *            Number of returned data
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);
	
	/**
	 * return number of queryShopList
	 * 
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
