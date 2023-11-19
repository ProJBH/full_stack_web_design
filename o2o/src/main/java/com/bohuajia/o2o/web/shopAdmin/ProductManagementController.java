package com.bohuajia.o2o.web.shopAdmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bohuajia.o2o.dto.ImageHolder;
import com.bohuajia.o2o.dto.ProductExecution;
import com.bohuajia.o2o.entity.Product;
import com.bohuajia.o2o.entity.ProductCategory;
import com.bohuajia.o2o.entity.Shop;
import com.bohuajia.o2o.enums.ProductStateEnum;
import com.bohuajia.o2o.exceptions.ProductOperationException;
import com.bohuajia.o2o.service.ProductCategoryService;
import com.bohuajia.o2o.service.ProductService;
import com.bohuajia.o2o.util.CodeUtil;
import com.bohuajia.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;

	// The maximum number of product detail images supported for uploading
	private static final int IMAGEMAXCOUNT = 6;

	/**
	 * Get the product list under the shop through the shop ID
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductListByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// Get the page number passed from the front end
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// Get the upper limit of the number of products returned per page requested from the front end
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		// Obtain shop information from the current session, mainly to obtain shopId
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		// Null value determination
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
			// Get the incoming conditions that need to be retrieved，
			// Including whether you need to filter the product list under a certain shop from a certain product category and fuzzy search product name
			// Filter conditions can be arranged and combined
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			String productName = HttpServletRequestUtil.getString(request, "productName");
			Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
			// Input query conditions and paging information for query, and return the corresponding product list and total number.
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
	 * Get product information by product id
	 * 
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductById(@RequestParam Long productId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// Null value determination
		if (productId > -1) {
			// Get product information
			Product product = productService.getProductById(productId);
			// Get product category list under current shop
			List<ProductCategory> productCategoryList = productCategoryService
					.getProductCategoryList(product.getShop().getShopId());
			modelMap.put("product", product);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// Verify verification code
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "Wrong verification code");
			return modelMap;
		}
		// Initialization of variables that receive front-end parameters, including product, thumbnail, and detail image list entity classes
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// If there is a file stream in the request, the relevant files (including thumbnails and details) will be retrieved.
			if (multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, thumbnail, productImgList);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "Upload image cannot be null");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			String productStr = HttpServletRequestUtil.getString(request, "productStr");
			// Try to get the form string stream passed from the front end and convert it into a Product entity class
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		// If the Product information, thumbnail image and detail image lists are non-empty, the product addition operation will begin.
		if (product != null && thumbnail != null && productImgList.size() > 0) {
			try {
				// Obtain the current shop's ID from the session and assign it to the product, reducing dependence on front-end data.
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				// Perform add operation
				ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "Enter product information");
		}
		return modelMap;
	}

	private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// Take the thumbnail and build the ImageHolder object
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
		if (thumbnailFile != null) {
			thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
		}
		// Take out the detailed image list and build a List<ImageHolder> list object, which supports up to six image uploads.
		for (int i = 0; i < IMAGEMAXCOUNT; i++) {
			CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
			if (productImgFile != null) {
				// If the i-th detailed picture file stream taken out is not empty, add it to the detailed picture list
				ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),
						productImgFile.getInputStream());
				productImgList.add(productImg);
			} else {
				// If the i-th detailed image file stream taken out is empty, the loop will be terminated.
				break;
			}
		}
		return thumbnail;
	}

	/**
	 * Modify product
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// Is it called when editing the product or when online and offline the product?
		// If it is the former, the verification code identification will be performed, 
		// and if the latter is the case, the verification code identification will be skipped.
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		// Check verification code
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "Wrong verification code");
			return modelMap;
		}
		// Initialization of variables that receive front-end parameters, including product, thumbnail, and detail image list entity classes
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// If there is a file stream in the request, the relevant files (including thumbnails and details) will be retrieved.
		try {
			if (multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, thumbnail, productImgList);
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			String productStr = HttpServletRequestUtil.getString(request, "productStr");
			// Try to get the form string stream passed from the front end and convert it into a Product entity class
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		// Null value determination
		if (product != null) {
			try {
				// Obtain the current shop's ID from the session and assign it to the product, reducing dependence on front-end data.
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				// Start modifying product information
				ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "Please enter product information");
		}
		return modelMap;
	}

	/**
	 * Encapsulate product query conditions into Product instances
	 * 
	 * @param shopId(mandatory)
	 * @param productCategoryId(optional)
	 * @param productName(optional)
	 * @return
	 */
	private Product compactProductCondition(long shopId, long productCategoryId, String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		// 	If there are requirements for specified categories, they will be added.
		if (productCategoryId != -1L) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		// If there is a requirement for fuzzy query of product name, it will be added.
		if (productName != null) {
			productCondition.setProductName(productName);
		}
		return productCondition;
	}
}
