package com.bohuajia.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bohuajia.o2o.dao.ShopDAO;
import com.bohuajia.o2o.dto.ImageHolder;
import com.bohuajia.o2o.dto.ShopExecution;
import com.bohuajia.o2o.entity.Shop;
import com.bohuajia.o2o.enums.ShopStateEnum;
import com.bohuajia.o2o.exceptions.ShopOperationException;
import com.bohuajia.o2o.service.ShopService;
import com.bohuajia.o2o.util.ImageUtil;
import com.bohuajia.o2o.util.PathUtil;

public class ShopServiceImpl implements ShopService{
	@Autowired
	private ShopDAO shopDao;
	
	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		// Get the relative value path of the shop image directory
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		shop.setShopImg(shopImgAddr);
	}
	
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
		// check null value
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			// init shopInfo
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// add shopInfo
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new ShopOperationException("Store creation failed");
			} else {
				if (thumbnail.getImage() != null) {
					// store images
					try {
						addShopImg(shop, thumbnail);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error:" + e.getMessage());
					}
					// update shop images addr
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("Update image address failed");
					}
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shop getByShopId(long shopId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail)
			throws ShopOperationException {
		// TODO Auto-generated method stub
		return null;
	}
}
