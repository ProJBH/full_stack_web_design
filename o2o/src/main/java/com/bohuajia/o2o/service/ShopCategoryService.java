package com.bohuajia.o2o.service;

import java.util.List;

import com.bohuajia.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	/**
	 * Get the ShopCategory list based on query conditions
	 * 
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
