package com.bohuajia.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bohuajia.o2o.BaseTest;
import com.bohuajia.o2o.dto.ImageHolder;
import com.bohuajia.o2o.dto.ShopExecution;
import com.bohuajia.o2o.entity.PersonInfo;
import com.bohuajia.o2o.entity.Region;
import com.bohuajia.o2o.entity.Shop;
import com.bohuajia.o2o.entity.ShopCategory;
import com.bohuajia.o2o.enums.ShopStateEnum;
import com.bohuajia.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testAddShop() throws ShopOperationException, FileNotFoundException {
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
		shop.setShopName("test shop 3");
		shop.setShopDesc("test3");
		shop.setShopAddr("test3");
		shop.setPhone("test3");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("Validating");
		File shopImg = new File("/Users/projbh/Github_projects/full_stack_web_design/image/Baymax.jpeg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder(shopImg.getName(), is);
		ShopExecution se = shopService.addShop(shop, imageHolder );
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
	}
}
