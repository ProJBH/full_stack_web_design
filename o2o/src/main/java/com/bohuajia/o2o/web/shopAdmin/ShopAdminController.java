package com.bohuajia.o2o.web.shopAdmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin", method = { RequestMethod.GET })
public class ShopAdminController {
	@RequestMapping(value = "/shopoperation")
	public String shopOperation() {
		// Forward to shop registration/edit page
		// Line 22 in spring-web.xml set prefix and suffix already
		return "shop/shopoperation";
	}

	@RequestMapping(value = "/shoplist")
	public String shopList() {
		// Redirect to shop list page
		return "shop/shoplist";
	}

	@RequestMapping(value = "/shopmanagement")
	public String shopManagement() {
		// Redirect to shop management page
		return "shop/shopmanagement";
	}
	
	@RequestMapping(value = "/productcategorymanagement", method = RequestMethod.GET)
	private String productCategoryManage() {
		// Redirect to product category management page
		return "shop/productcategorymanagement";
	}
	
	@RequestMapping(value = "/productoperation")
	public String productOperation() {
		// Forward to product add/edit page
		return "shop/productoperation";
	}
	
	@RequestMapping(value = "/productmanagement")
	public String productManagement() {
		// Forward to product management page
		return "shop/productmanagement";
	}
}
