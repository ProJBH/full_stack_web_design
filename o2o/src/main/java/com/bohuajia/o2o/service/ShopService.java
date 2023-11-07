package com.bohuajia.o2o.service;

import com.bohuajia.o2o.dto.ImageHolder;
import com.bohuajia.o2o.dto.ShopExecution;
import com.bohuajia.o2o.entity.Shop;
import com.bohuajia.o2o.exceptions.ShopOperationException;

public interface ShopService {
	/**
	 * Return the corresponding store list according to shopCondition paging
	 * 
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

	/**
	 * Get shop info though shopId
	 * 
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);

	/**
	 * Update shop information, including image processing
	 * 
	 * @param shop
	 * @param shopImg
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

	/**
	 * Register shop information, including image processing
	 * 
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
}