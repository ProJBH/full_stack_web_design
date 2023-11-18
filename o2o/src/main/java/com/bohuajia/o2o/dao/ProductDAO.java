package com.bohuajia.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bohuajia.o2o.entity.Product;

public interface ProductDAO {
	/**
	 * Query the product list and paginate it. The conditions that can be entered
	 * are: product name (fuzzy), product status, shop ID, product category
	 * 
	 * @param productCondition
	 * @param beginIndex
	 * @param pageSize
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * Query the total number of corresponding products
	 * 
	 * @param productCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondition") Product productCondition);

	/**
	 * Query unique product information by productId
	 * 
	 * @param productId
	 * @return
	 */
	Product queryProductById(long productId);

	/**
	 * Insert product
	 * 
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);

	/**
	 * Update product
	 * 
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);

	/**
	 * Before deleting a product category, set the product category ID to empty
	 * 
	 * @param productCategoryId
	 * @return
	 */
	int updateProductCategoryToNull(long productCategoryId);

	/**
	 * Delete product
	 * 
	 * @param productId
	 * @return
	 */
	int deleteProduct(@Param("productId") long productId, @Param("shopId") long shopId);
}
