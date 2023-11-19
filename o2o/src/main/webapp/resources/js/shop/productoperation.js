$(function() {
	// Get productId paramater from URL
	var productId = getQueryString('productId');
	// Get product information URL from productId
	var infoUrl = '/o2o/shopadmin/getproductbyid?productId=' + productId;
	// Get the URL of the product category list set by the current shop
	var categoryUrl = '/o2o/shopadmin/getproductcategorylist';
	// URL to update product information
	var productPostUrl = '/o2o/shopadmin/modifyproduct';
	// Since product addition and editing use the same page,
	// This identifier is used to indicate whether this is an add or edit operation.
	var isEdit = false;
	if (productId) {
		// If there is a productId, it is an editing operation.
		getInfo(productId);
		isEdit = true;
	} else {
		getCategory();
		productPostUrl = '/o2o/shopadmin/addproduct';
	}

	// Get the infomation from edited product and assign to the form
	function getInfo(id) {
		$
			.getJSON(
				infoUrl,
				function(data) {
					if (data.success) {
						// Get the product object information from the returned JSON and assign it to the form
						var product = data.product;
						$('#product-name').val(product.productName);
						$('#product-desc').val(product.productDesc);
						$('#priority').val(product.priority);
						$('#point').val(product.point);
						$('#normal-price').val(product.normalPrice);
						$('#promotion-price').val(
							product.promotionPrice);
						// Get the original product category and a list of all product categories in the shop
						var optionHtml = '';
						var optionArr = data.productCategoryList;
						var optionSelected = product.productCategory.productCategoryId;
						// Generate a front-end HTML product category list, and select the product category before editing by default
						optionArr
							.map(function(item, index) {
								var isSelect = optionSelected === item.productCategoryId ? 'selected'
									: '';
								optionHtml += '<option data-value="'
									+ item.productCategoryId
									+ '"'
									+ isSelect
									+ '>'
									+ item.productCategoryName
									+ '</option>';
							});
						$('#category').html(optionHtml);
					}
				});
	}

	// Provide a list of all product categories under the shop for product addition operations
	function getCategory() {
		$.getJSON(categoryUrl, function(data) {
			if (data.success) {
				var productCategoryList = data.data;
				var optionHtml = '';
				productCategoryList.map(function(item, index) {
					optionHtml += '<option data-value="'
						+ item.productCategoryId + '">'
						+ item.productCategoryName + '</option>';
				});
				$('#category').html(optionHtml);
			}
		});
	}

	// For the product details image control group, if the last element of the control group changes (that is, an image is uploaded),
	// And if the total number of controls does not reach 6, a new file upload control will be generated.
	$('.detail-img-div').on('change', '.detail-img:last-child', function() {
		if ($('.detail-img').length < 6) {
			$('#detail-img').append('<input type="file" class="detail-img">');
		}
	});

	// The event response of the submit button provides different responses to product addition and editing operations.
	$('#submit').click(
		function() {
			// Create a product json object and obtain the corresponding attribute values ​​from the form
			var product = {};
			product.productName = $('#product-name').val();
			product.productDesc = $('#product-desc').val();
			product.priority = $('#priority').val();
			product.point = $('#point').val();
			product.normalPrice = $('#normal-price').val();
			product.promotionPrice = $('#promotion-price').val();
			// Get selected product category value
			product.productCategory = {
				productCategoryId: $('#category').find('option').not(
					function() {
						return !this.selected;
					}).data('value')
			};
			product.productId = productId;

			// Get thumbnail file stream
			var thumbnail = $('#small-img')[0].files[0];
			// Generate form objects to receive parameters and pass them to the background
			var formData = new FormData();
			formData.append('thumbnail', thumbnail);
			// Traverse the product detailed image control and obtain the file stream
			$('.detail-img').map(
				function(index, item) {
					// Determine whether the control has selected a file
					if ($('.detail-img')[index].files.length > 0) {
						// Assign the i-th file stream to the form key-value pair with key productImgi
						formData.append('productImg' + index,
							$('.detail-img')[index].files[0]);
					}
				});
			// Convert the product json object into a character stream and save it to the key-value pair of the form object whose key is productStr.
			formData.append('productStr', JSON.stringify(product));
			// Get the verification code entered in the form
			var verifyCodeActual = $('#j_captcha').val();
			if (!verifyCodeActual) {
				$.toast('Please enter verification code！');
				return;
			}
			formData.append("verifyCodeActual", verifyCodeActual);
			// Submit data to background processing related operations
			$.ajax({
				url: productPostUrl,
				type: 'POST',
				data: formData,
				contentType: false,
				processData: false,
				cache: false,
				success: function(data) {
					if (data.success) {
						$.toast('Submit successed！');
						$('#captcha_img').click();
					} else {
						$.toast('Submit failed！');
						$('#captcha_img').click();
					}
				}
			});
		});

});