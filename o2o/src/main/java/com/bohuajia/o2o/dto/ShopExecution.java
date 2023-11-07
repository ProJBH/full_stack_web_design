package com.bohuajia.o2o.dto;

import java.util.List;

import com.bohuajia.o2o.entity.Shop;
import com.bohuajia.o2o.enums.ShopStateEnum;

public class ShopExecution {
	// result status
	private int state;

	// status information
	private String stateInfo;

	// number of affected shop 
	private int count;

	// operating shop(add, delete, modify)
	private Shop shop;

	// shop list(Used when querying store list)
	private List<Shop> shopList;

	public ShopExecution() {

	}

	// Constructor used when store operation fails
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// Constructor used when store operation success
	public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}

	// Constructor used when store operation success
	public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

}
