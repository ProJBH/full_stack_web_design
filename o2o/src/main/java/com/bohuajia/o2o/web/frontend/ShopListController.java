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

import com.bohuajia.o2o.dto.ShopExecution;
import com.bohuajia.o2o.entity.Region;
import com.bohuajia.o2o.entity.Shop;
import com.bohuajia.o2o.entity.ShopCategory;
import com.bohuajia.o2o.service.RegionService;
import com.bohuajia.o2o.service.ShopCategoryService;
import com.bohuajia.o2o.service.ShopService;
import com.bohuajia.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
	@Autowired
	private RegionService regionService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private ShopService shopService;

	/**
	 * Returns the ShopCategory list (secondary or first-level) in the product list page, and the regional information list
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// Try to get parentId from frontend request
		long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		if (parentId != -1) {
			// If parentId exists, retrieve the secondary ShopCategory list under the first-level ShopCategory.
			try {
				ShopCategory shopCategoryCondition = new ShopCategory();
				ShopCategory parent = new ShopCategory();
				parent.setShopCategoryId(parentId);
				shopCategoryCondition.setParent(parent);
				shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			try {
				// If the parentId does not exist, all first-level ShopCategories are retrieved (the user selects the list of all stores on the home page)
				shopCategoryList = shopCategoryService.getShopCategoryList(null);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}
		modelMap.put("shopCategoryList", shopCategoryList);
		List<Region> regionList = null;
		try {
			// Get region list information
			regionList = regionService.getRegionList();
			modelMap.put("regionList", regionList);
			modelMap.put("success", true);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	/**
	 * Get the list of shops under specified query conditions
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listshops", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// Get page number
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// Get the number of data items to be displayed on a page
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		// non-null identify
		if ((pageIndex > -1) && (pageSize > -1)) {
			// Try to get the first level category ID
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			// Try to get a specific secondary category Id
			long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
			// Try to get the region Id
			int regionId = HttpServletRequestUtil.getInt(request, "regionId");
			// Try to get the name of fuzzy query
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
			// Get the combined query conditions
			Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, regionId, shopName);
			// Get the shop list based on query conditions and paging information, and return the total number
			ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex");
		}

		return modelMap;
	}

	/**
	 * Combine the query conditions and encapsulate the conditions into the ShopCondition object and return
	 * 
	 * @param parentId
	 * @param shopCategoryId
	 * @param regionId
	 * @param shopName
	 * @return
	 */
	private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int regionId, String shopName) {
		Shop shopCondition = new Shop();
		if (parentId != -1L) {
			// Query the list of shops in all second-level ShopCategories under a certain first-level ShopCategory
			ShopCategory childCategory = new ShopCategory();
			ShopCategory parentCategory = new ShopCategory();
			parentCategory.setShopCategoryId(parentId);
			childCategory.setParent(parentCategory);
			shopCondition.setShopCategory(childCategory);
		}
		if (shopCategoryId != -1L) {
			// Query the list of shops under a certain secondary ShopCategory
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(shopCategory);
		}
		if (regionId != -1L) {
			// Query the list of shops located under a certain region ID
			Region region = new Region();
			region.setRegionId(regionId);
			shopCondition.setRegion(region);
		}

		if (shopName != null) {
			// Query the list of shops whose name contains shopName
			shopCondition.setShopName(shopName);
		}
		// The shops displayed on the front end are all shops that have been successfully reviewed.
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}
}
