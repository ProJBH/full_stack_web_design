package com.bohuajia.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bohuajia.o2o.dao.ProductCategoryDao;
import com.bohuajia.o2o.dto.ProductCategoryExecution;
import com.bohuajia.o2o.entity.ProductCategory;
import com.bohuajia.o2o.enums.ProductCategoryStateEnum;
import com.bohuajia.o2o.exceptions.ProductCategoryOperationException;
import com.bohuajia.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryDao productCategoryDao;

	//@Autowired
	//private ProductDAO productDao;

	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectedNum <= 0) {
					throw new ProductCategoryOperationException("Product category creation failed");
				} else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}

			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory error: " + e.getMessage());
			}
		} else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		// Disassociate the product in tb_product from the productegoryId
		//try {
		//	int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
		//	if (effectedNum < 0) {
		//		throw new ProductCategoryOperationException("Product category update failed");
		//	}
		//} catch (Exception e) {
		//	throw new ProductCategoryOperationException("deleteProductCategory error: " + e.getMessage());
		//}
		// delete current productCategory
		try {
			int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if (effectedNum <= 0) {
				throw new ProductCategoryOperationException("Product category delete failed");
			} else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
		}
	}
}
