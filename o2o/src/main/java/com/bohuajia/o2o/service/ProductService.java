package com.bohuajia.o2o.service;

import java.util.List;

import com.bohuajia.o2o.dto.ImageHolder;
import com.bohuajia.o2o.dto.ProductExecution;
import com.bohuajia.o2o.entity.Product;
import com.bohuajia.o2o.exceptions.ProductOperationException;

public interface ProductService {
	/**
	 * Query the product list and paginate it. The conditions that can be entered are: 
	 * product name (fuzzy), product status, store ID, product category
	 * 
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	/**
	 * Query unique product information by product ID
	 * 
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);

	/**
	 * Add product information and image processing
	 * 
	 * @param product
	 * @param thumbnail
	 * @param productImgs
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException;

	/**
	 * Update product information and image processing
	 * 
	 * @param product
	 * @param thumbnail
	 * @param productImgs
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationException;
}
