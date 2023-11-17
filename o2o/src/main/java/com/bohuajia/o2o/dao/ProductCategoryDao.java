package com.bohuajia.o2o.dao;

import java.util.List;
import com.bohuajia.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
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
}
