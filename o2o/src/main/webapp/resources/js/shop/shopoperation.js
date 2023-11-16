/**
 * 1. Get information such as shop classification and region from the backend, and fill it into the front-end html
 * 2. Get the form information, forward them to backend and register the shop
 */

// When loading .js file, call function()
$(function() {
	// Get the value of shopId parameter from URL
	var shopId = getQueryString('shopid');
	// Since shop registration and editing use the same page,
	// This identifier is used to indicate whether this is an add or edit operation.
	var isEdit = shopId ? true : false;
	// The URL used to initialize the shop category and region list when registering the shop.
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	// URL for registering shop.
	var registerShopUrl = '/o2o/shopadmin/registershop';
	// Before updating shop, need to obtain shop info. Here is the URL to obtain the current shop info.
	var shopInfoUrl = "/o2o/shopadmin/getshopbyid?shopId=" + shopId;
	// Modify shop info URL
	var editShopUrl = '/o2o/shopadmin/modifyshop';
	// Determine whether it is an editing operation or a registration operation
	if (!isEdit) {
		getShopInitInfo();
	} else {
		getShopInfo(shopId);
	}
	// Get shop info by shop id
	function getShopInfo(shopId) {
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				// If the access is successful, 
				// the form elements will be assigned values based on the shop information passed from the background.
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				// Select the original shop category value for the shop category
				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryId + '" selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';
				var tempRegionHtml = '';
				// init region list
				data.regionList.map(function(item, index) {
					tempRegionHtml += '<option data-id="' + item.regionId + '">'
							+ item.regionName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				// cant select shop category
				$('#shop-category').attr('disabled', 'disabled');
				$('#region').html(tempRegionHtml);
				// Select the original region as default 
				$("#region option[data-id='" + shop.region.regionId + "']").attr(
						"selected", "selected");
			}
		});
	}
	// Obtain all secondary shop categories and region information, and assign them to the category list and region list respectively.
	function getShopInitInfo() {
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = '';
				var tempRegionHtml = '';
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				data.regionList.map(function(item, index) {
					tempRegionHtml += '<option data-id="' + item.regionId + '">'
							+ item.regionName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#region').html(tempRegionHtml);
			}
		});
	}
	
	// The event response of the submit button provides different responses to shop registration and editing operations.
	$('#submit').click(function() {
		// create shop obj
		var shop = {};
		if (isEdit) {
			// if action is editing，then give shopId value
			shop.shopId = shopId;
		}
		// Get the data in the form and fill it into the corresponding shop attributes
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		// Select the selected shop category
		shop.shopCategory = {
			shopCategoryId : $('#shop-category').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		// Select the selected region information
		shop.region = {
			regionId : $('#region').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		// Get the uploaded image file stream
		var shopImg = $('#shop-img')[0].files[0];
		// Generate form obj to receive parameters and pass them to the backend
		var formData = new FormData();
		// add image to form obj
		formData.append('shopImg', shopImg);
		// Convert the shop json obj into character stream, save it to the key-value pair(key is shopStr).
		formData.append('shopStr', JSON.stringify(shop));
		// Get the verification code entered in the form
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('Please enter verification code！');
			return;
		}
		formData.append('verifyCodeActual', verifyCodeActual);
		// Submit data to backend processing related operations
		$.ajax({
			url : (isEdit ? editShopUrl : registerShopUrl),
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('Submitted successfully！');
					if (!isEdit) {
						// If it is a registration operation, it will return to the shop list page after success.
						window.location.href = "/o2o/shopadmin/shoplist";
					}
				} else {
					$.toast('Submitted failed！' + data.errMsg);
				}
				// click on the verification code image, code will change
				$('#captcha_img').click();
			}
		});
	});

})