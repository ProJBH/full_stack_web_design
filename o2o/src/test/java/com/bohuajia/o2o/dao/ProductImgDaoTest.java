package com.bohuajia.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.bohuajia.o2o.BaseTest;
import com.bohuajia.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
	@Autowired
	private ProductImgDAO productImgDao;

	@Test
	public void testABatchInsertProductImg() throws Exception {
		// Add two detailed image records to the product with productId 1
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("IMG 1");
		productImg1.setImgDesc("TEST IMG 1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(16L);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("IMG 2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(16L);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
	}

	@Test
	public void testBQueryProductImgList() {
		// Check whether the product with productId 1 has and only two product detail images
		List<ProductImg> productImgList = productImgDao.queryProductImgList(16L);
		assertEquals(2, productImgList.size());
	}

	@Test
	public void testCDeleteProductImgByProductId() throws Exception {
		// Delete the two newly added product details image records
		long productId = 16;
		int effectedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2, effectedNum);
	}
}
