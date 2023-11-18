package com.bohuajia.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bohuajia.o2o.BaseTest;
import com.bohuajia.o2o.dto.ImageHolder;
import com.bohuajia.o2o.dto.ProductExecution;
import com.bohuajia.o2o.entity.Product;
import com.bohuajia.o2o.entity.ProductCategory;
import com.bohuajia.o2o.entity.Shop;
import com.bohuajia.o2o.enums.ProductStateEnum;
import com.bohuajia.o2o.exceptions.ShopOperationException;

public class ProductServiceTest extends BaseTest {
	@Autowired
	private ProductService productService;

	@Test
	public void testAddProduct() throws ShopOperationException, FileNotFoundException {
		// Create a product instance with shopId 1 and productCategoryId 1 and assign values to its member variables
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("test product 1");
		product.setProductDesc("test product 1");
		product.setPriority(20);
		product.setPoint(0);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		// Create a thumbnail file stream
		File thumbnailFile = new File("/Users/projbh/Github_projects/full_stack_web_design/image/minions.jpg");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
		// Create two product detail image file streams and add them to the detail image list
		File productImg1 = new File("/Users/projbh/Github_projects/full_stack_web_design/image/minions.jpg");
		InputStream is1 = new FileInputStream(productImg1);
		File productImg2 = new File("/Users/projbh/Github_projects/full_stack_web_design/image/Baymax.jpeg");
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(), is1));
		productImgList.add(new ImageHolder(productImg2.getName(), is2));
		// Add product and verify
		ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}

	@Test
	@Ignore
	public void testModifyProduct() throws ShopOperationException, FileNotFoundException {
		// Create a product instance with shopId 1 and productCategoryId 1 and assign values to its member variables
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setProductId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("formal product");
		product.setProductDesc("formal product");
		// Create a thumbnail file stream
		File thumbnailFile = new File("/Users/baidu/work/image/ercode.jpg");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
		// Create two product detail image file streams and add them to the detail image list
		File productImg1 = new File("/Users/baidu/work/image/xiaohuangren.jpg");
		InputStream is1 = new FileInputStream(productImg1);
		File productImg2 = new File("/Users/baidu/work/image/dabai.jpg");
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(), is1));
		productImgList.add(new ImageHolder(productImg2.getName(), is2));
		// Add product and verify
		ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}

}
