package com.bohuajia.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bohuajia.o2o.dao.ProductDAO;
import com.bohuajia.o2o.dao.ProductImgDAO;
import com.bohuajia.o2o.dto.ImageHolder;
import com.bohuajia.o2o.dto.ProductExecution;
import com.bohuajia.o2o.entity.Product;
import com.bohuajia.o2o.entity.ProductImg;
import com.bohuajia.o2o.enums.ProductStateEnum;
import com.bohuajia.o2o.exceptions.ProductOperationException;
import com.bohuajia.o2o.service.ProductService;
import com.bohuajia.o2o.util.ImageUtil;
import com.bohuajia.o2o.util.PageCalculator;
import com.bohuajia.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDAO productDao;
	@Autowired
	private ProductImgDAO productImgDao;

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		// The page number is converted into a row code in the database, 
		// DAO layer is called to retrieve the product list of the specified page number.
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		// Based on the same query conditions, 
		// return the total number of products under the query conditions.
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}

	@Override
	@Transactional
	// 1. Process the thumbnail, obtain the relative path of the thumbnail and assign it to product
	// 2. Write product information to tb_product and obtain productId
	// 3. Combine productId to batch process product details
	// 4. Insert the product detail image list into tb_product_img in batches
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationException {
		// Null value determination
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// Set default attributes for products
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			// The default is the state of the new product
			product.setEnableStatus(1);
			// Add product thumbnail if it's not null
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				// Create product info
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("Failed to create product");
				}
			} catch (Exception e) {
				throw new ProductOperationException("Failed to create product:" + e.toString());
			}
			// Add product details image if it is not null
			if (productImgHolderList != null && productImgHolderList.size() > 0) {
				addProductImgList(product, productImgHolderList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			// If the passed parameter is null, return empty err msg.
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	// 1.If the thumbnail parameter has a value, the thumbnail is processed,
	// If the thumbnail image originally exists, delete it first and then add a new image. 
	// Then obtain the relative path of the thumbnail image and assign it to the product.
	// 2.If the product details image list parameter has a value, 
	// perform the same operation on the product details image list.
	// 3.Clear all the original product details of the product under tb_product_img
	// 4.Update tb_product_img and tb_product information
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productImgHolderList) throws ProductOperationException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// Set default value
			product.setLastEditTime(new Date());
			// If the product thumbnail is not empty and the original thumbnail is not empty, 
			// delete the original thumbnail and add
			if (thumbnail != null) {
				// Get the original information first, because the original information contains the original image address
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}
			// If there is a newly saved product detail image, delete the original one and add a new one.
			if (productImgHolderList != null && productImgHolderList.size() > 0) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgHolderList);
			}
			try {
				// pudate product info
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("Failed to update product information");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				throw new ProductOperationException("Failed to update product information:" + e.toString());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * Add thumbnail
	 * 
	 * @param product
	 * @param thumbnail
	 */
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}

	/**
	 * Add pictures in batches
	 * 
	 * @param product
	 * @param productImgHolderList
	 */
	private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
		// Get the image storage path and store it directly under the folder of the corresponding shop.
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		// Traverse the image once to process it and add it to the productImg entity class
		for (ImageHolder productImgHolder : productImgHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		// If there are indeed images that need to be added, perform a batch add operation.
		if (productImgList.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					throw new ProductOperationException("Failed to create product listing image");
				}
			} catch (Exception e) {
				throw new ProductOperationException("Failed to create product listing image:" + e.toString());
			}
		}
	}

	/**
	 * Delete all detailed images under a product
	 * 
	 * @param productId
	 */
	private void deleteProductImgList(long productId) {
		// Get the original image based on productId
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		// delete the original image
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		// Delete the information of the original image in the database
		productImgDao.deleteProductImgByProductId(productId);
	}
}
