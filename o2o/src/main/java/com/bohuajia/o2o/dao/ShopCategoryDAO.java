package com.bohuajia.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bohuajia.o2o.entity.ShopCategory;

public interface ShopCategoryDAO {
	/**
	 * Returns a list of shop categories based on the incoming query conditions
	 * 
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
