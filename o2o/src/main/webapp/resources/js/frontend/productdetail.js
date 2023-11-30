$(function() {
	// Get the productId from the URL in the address bar.
	var productId = getQueryString('productId');
	// URL for getting product information
	var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId='
		+ productId;
	// Visit the backend to get information about the item and render it
	$
		.getJSON(
			productUrl,
			function(data) {
				if (data.success) {
					// Get product information
					var product = data.product;
					// Product Thumbnails
					$('#product-img').attr('src',
						product.imgAddr);
					// Product update time
					$('#product-time').text(
						new Date(product.lastEditTime)
							.Format("yyyy-MM-dd"));
					if (product.point != undefined) {
						$('#product-point').text(
							'Purchase to get' + product.point + 'point(s)');
					}
					// Product name
					$('#product-name').text(product.productName);
					// Product desc
					$('#product-desc').text(product.productDesc);
					// Commodity price display logic, mainly to determine whether the original price of the current price is empty , 
					// all are empty is not to display the price columns
					if (product.normalPrice != undefined
						&& product.promotionPrice != undefined) {
						// If neither the current price nor the original price is null then both are displayed and the original price is given a deletion sign
						$('#price').show();
						$('#normalPrice').html(
							'<del>' + product.normalPrice + ' NOK'
							+ '</del>');
						$('#promotionPrice').text(
							product.promotionPrice + ' NOK');
					} else if (product.normalPrice != undefined
						&& product.promotionPrice == undefined) {
						// If the original price is not empty and the current price is empty then only the original price will be displayed.
						$('#price').show();
						$('#promotionPrice').text(
							product.normalPrice + ' NOK');
					} else if (product.normalPrice == undefined
						&& product.promotionPrice != undefined) {
						// If the current price is not empty and the original price is empty then only the current price will be displayed.
						$('#promotionPrice').text(
							product.promotionPrice + ' NOK');
					}
					var imgListHtml = '';
					// Iterate through the list of product detail images and generate batch img tags
					product.productImgList.map(function(item, index) {
						imgListHtml += '<div> <img src="'
							+ item.imgAddr
							+ '" width="100%" /></div>';
					});
					//if (data.needQRCode) {
					// If the customer is logged in, a QR code of the purchased product will be generated for the merchant to scan.
					//imgListHtml += '<div> <img src="/o2o/frontend/generateqrcode4product?productId='
					//	+ product.productId
					//	+ '" width="100%"/></div>';
					//}
					$('#imgList').html(imgListHtml);
				}
			});
	// Cilck to open right sidebar
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	$.init();
});
