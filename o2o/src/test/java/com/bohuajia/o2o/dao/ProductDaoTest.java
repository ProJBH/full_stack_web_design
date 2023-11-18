package com.bohuajia.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.bohuajia.o2o.BaseTest;
import com.bohuajia.o2o.entity.Product;
import com.bohuajia.o2o.entity.ProductCategory;
import com.bohuajia.o2o.entity.ProductImg;
import com.bohuajia.o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

	@Autowired
	private ProductDAO productDao;
	@Autowired
	private ProductImgDAO productImgDao;

	@Test
	@Ignore
	public void testAInsertProduct() throws Exception {
		Shop shop1 = new Shop();
		shop1.setShopId(1L);
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryId(1L);
		// Initialize three product instances and add them to the store with shopId 1.
		// At the same time, the product category ID is also 1
		Product product1 = new Product();
		product1.setProductName("test 1");
		product1.setProductDesc("test Desc1");
		product1.setImgAddr("test 1 addr");
		product1.setPriority(1);
		product1.setEnableStatus(1);
		product1.setCreateTime(new Date());
		product1.setLastEditTime(new Date());
		product1.setPoint(0);
		product1.setShop(shop1);
		product1.setProductCategory(pc1);
		Product product2 = new Product();
		product2.setProductName("test 2");
		product2.setProductDesc("test Desc2");
		product2.setImgAddr("test 2 addr");
		product2.setPriority(2);
		product2.setEnableStatus(0);
		product2.setCreateTime(new Date());
		product2.setLastEditTime(new Date());
		product2.setPoint(0);
		product2.setShop(shop1);
		product2.setProductCategory(pc1);
		Product product3 = new Product();
		product3.setProductName("sth diff 3");
		product3.setProductDesc("sth diff Desc3");
		product3.setImgAddr("sth diff 3 addr");
		product3.setPriority(3);
		product3.setEnableStatus(1);
		product3.setCreateTime(new Date());
		product3.setLastEditTime(new Date());
		product3.setPoint(0);
		product3.setShop(shop1);
		product3.setProductCategory(pc1);
		// Determine whether the addition is successful
		int effectedNum = productDao.insertProduct(product1);
		assertEquals(1, effectedNum);
		effectedNum = productDao.insertProduct(product2);
		assertEquals(1, effectedNum);
		effectedNum = productDao.insertProduct(product3);
		assertEquals(1, effectedNum);
	}

	@Test
	@Ignore
	public void testBQueryProductList() throws Exception {
		Product productCondition = new Product();
		// Paginated query, expected to return three results
		List<Product> productList = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(3, productList.size());
		// Query the total number of products named test
		int count = productDao.queryProductCount(productCondition);
		assertEquals(3, count);
		// Using product name fuzzy query, two results are expected to be returned
		productCondition.setProductName("test");
		productList = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(2, productList.size());
		count = productDao.queryProductCount(productCondition);
		assertEquals(2, count);
	}

	@Test
	@Ignore
	public void testCQueryProductByProductId() throws Exception {
		long productId = 13;
		// Initialize two product detail image instances as detail images for the product with productId 1
		// Batch insert into product details chart
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("img 1");
		productImg1.setImgDesc("test img 1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(productId);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("img 2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(productId);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
		// Query product information with productId 1 and verify whether the size of the returned detail image instance list is 2
		Product product = productDao.queryProductById(productId);
		assertEquals(2, product.getProductImgList().size());
		// Delete the two newly added instances of product detail images
		effectedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2, effectedNum);
	}

	@Test
	@Ignore
	public void testDUpdateProduct() throws Exception {
		Product product = new Product();
		ProductCategory pc = new ProductCategory();
		Shop shop = new Shop();
		shop.setShopId(1L);
		pc.setProductCategoryId(2L);
		product.setProductId(13L);
		product.setShop(shop);
		product.setProductName("Second Product");
		product.setProductCategory(pc);
		// Modify the name of the product with productId 1
		// and product category and verify whether the number of affected rows is 1
		int effectedNum = productDao.updateProduct(product);
		assertEquals(1, effectedNum);
	}

	@Test
	@Ignore
	public void testEUpdateProductCategoryToNull() {
		// Set the product category of the product under the product category with productCategoryId 2 to empty.
		int effectedNum = productDao.updateProductCategoryToNull(2L);
		assertEquals(1, effectedNum);
	}

	@Test
	@Ignore
	public void testFDeleteShopAuthMap() throws Exception {
		// Clear the products added by insert method
		Product productCondition = new Product();
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		productCondition.setProductCategory(pc);
		// Find the three new test data in the product table by entering productCategoryId as 1.
		List<Product> productList = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(3, productList.size());
		// Delete these three pieces of data in a loop
		for (Product p : productList) {
			int effectedNum = productDao.deleteProduct(p.getProductId(), 1);
			assertEquals(1, effectedNum);
		}
	}
}
