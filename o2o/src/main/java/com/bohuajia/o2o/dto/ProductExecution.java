package com.bohuajia.o2o.dto;

import java.util.List;

import com.bohuajia.o2o.entity.Product;
import com.bohuajia.o2o.enums.ProductStateEnum;

public class ProductExecution {
	// Result state
	private int state;

	private String stateInfo;

	// Amount of product
	private int count;

	// Product to operate (used when adding, deleting or modifying products)
	private Product product;

	// Obtained product list (used when querying product list)
	private List<Product> productList;

	public ProductExecution() {
	}

	// Constructor when action fail
	public ProductExecution(ProductStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// Constructor when action success (signal product)
	public ProductExecution(ProductStateEnum stateEnum, Product product) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.product = product;
	}

	// Constructor when action success (product list)
	public ProductExecution(ProductStateEnum stateEnum, List<Product> productList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productList = productList;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

}
