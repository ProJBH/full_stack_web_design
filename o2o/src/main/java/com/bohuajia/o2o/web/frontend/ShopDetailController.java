package com.bohuajia.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohuajia.o2o.dto.ProductExecution;
import com.bohuajia.o2o.entity.Product;
import com.bohuajia.o2o.entity.ProductCategory;
import com.bohuajia.o2o.entity.Shop;
import com.bohuajia.o2o.service.ProductCategoryService;
import com.bohuajia.o2o.service.ProductService;
import com.bohuajia.o2o.service.ShopService;
import com.bohuajia.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * Get shop information and a list of product categories under the shop.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// Get the shopId from the frontend.
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		Shop shop = null;
		List<ProductCategory> productCategoryList = null;
		if (shopId != -1) {
			// Get shop information by search shopId.
			shop = shopService.getByShopId(shopId);
			// Get the list of product categories under the shop
			productCategoryList = productCategoryService.getProductCategoryList(shopId);
			modelMap.put("shop", shop);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}

	/**
	 * List all the products under the shop by query condition.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// Get page number
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// Get the number of items to be displayed on a page
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		// Get shop ID
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		// NULL value verification
		if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
			// Try to get the product category Id
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			// Trying to get a fuzzy lookup for a product name
			String productName = HttpServletRequestUtil.getString(request, "productName");
			// Combined query conditions
			Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
			// Returns a list of products and the total number of products according to the
			// incoming query and paging information.
			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	/**
	 * Combine the query conditions and encapsulate the conditions in the
	 * ProductCondition object and return it.
	 * 
	 * @param shopId
	 * @param productCategoryId
	 * @param productName
	 * @return
	 */
	private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		if (productCategoryId != -1L) {
			// Query the list of products under a product category
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if (productName != null) {
			// Query the list of shops with productName in their name.
			productCondition.setProductName(productName);
		}
		// Only products with a status of "online" are allowed to be selected.
		productCondition.setEnableStatus(1);
		return productCondition;
	}
}
