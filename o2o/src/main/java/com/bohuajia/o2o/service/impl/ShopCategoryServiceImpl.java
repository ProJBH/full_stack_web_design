package com.bohuajia.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohuajia.o2o.dao.ShopCategoryDAO;
import com.bohuajia.o2o.entity.ShopCategory;
import com.bohuajia.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
	@Autowired
	private ShopCategoryDAO shopCategoryDao;
	
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}
}
