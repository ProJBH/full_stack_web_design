package com.bohuajia.o2o.service;

import java.util.List;

import com.bohuajia.o2o.dto.ProductCategoryExecution;
import com.bohuajia.o2o.entity.ProductCategory;
import com.bohuajia.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {
	/**
	 * Query all product category information under a specified shop
	 * 
	 * @param long shopId
	 * @return List<ProductCategory>
	 */
	List<ProductCategory> getProductCategoryList(long shopId);

	/**
	 * 
	 * @param productCategory
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException;
}
