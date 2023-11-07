package com.bohuajia.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bohuajia.o2o.BaseTest;
import com.bohuajia.o2o.entity.PersonInfo;
import com.bohuajia.o2o.entity.Region;
import com.bohuajia.o2o.entity.Shop;
import com.bohuajia.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDAO shopDao;
	@Test
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Region region = new Region();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		region.setRegionId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setRegion(region);
		shop.setShopCategory(shopCategory);
		shop.setShopName("test shop");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("verifying...");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
}
