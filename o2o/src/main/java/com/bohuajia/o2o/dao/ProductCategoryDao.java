package com.bohuajia.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bohuajia.o2o.entity.ProductCategory;

public interface ProductCategoryDAO {
	/**
	 * Query product category by shop id
	 * 
	 * @param long shopId
	 * @return List<ProductCategory>
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);

	/**
	 * Add product categories in batches
	 * 
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);

	/**
	 * Delete selected product category
	 * 
	 * @param productCategoryId
	 * @param shopId
	 * @return effectedNum
	 */
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
