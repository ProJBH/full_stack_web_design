package com.bohuajia.o2o.dao;

import java.util.List;

import com.bohuajia.o2o.entity.ProductImg;

public interface ProductImgDAO {

	/**
	 * List the details of a product
	 * 
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(long productId);

	/**
	 * Add product detail images in batches
	 * 
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);

	/**
	 * Delete all detailed pictures under the specified product
	 * 
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
}
