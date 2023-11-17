package com.bohuajia.o2o.service;

import java.util.List;

import com.bohuajia.o2o.entity.ProductCategory;

public interface ProductCategoryService {
	/**
	 * Query all product category information under a specified shop
	 * 
	 * @param long shopId
	 * @return List<ProductCategory>
	 */
	List<ProductCategory> getProductCategoryList(long shopId);
}
