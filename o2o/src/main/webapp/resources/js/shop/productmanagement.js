$(function() {
	// Get the URL of the product list under this shop
	var listUrl = '/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999';
	// Product modify URL
	var statusUrl = '/o2o/shopadmin/modifyproduct';
	getList();
	/**
	 * Get the product list under this shop
	 * 
	 * @returns
	 */
	function getList() {
		// Get the product list of this shop from the backend
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var productList = data.productList;
				var tempHtml = '';
				// Traverse each piece of product information and display it in one row. The column information includes:
				// Product name, priority, on/off shelves (including productId), edit button (including productId)
				// Preview (including productId)
				productList.map(function(item, index) {
					var textOp = "Offline";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						// If the status value is 0, it indicates that the product has been removed from the shelves(offline), 
						// and the operation changes to Online
						textOp = "Online";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					// Splice row information for each item
					tempHtml += '' + '<div class="row row-product">'
							+ '<div class="col-33">'
							+ item.productName
							+ '</div>'
							+ '<div class="col-20">'
							+ item.point
							+ '</div>'
							+ '<div class="col-40">'
							+ '<a href="#" class="edit" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">Modify</a>'
							+ '<a href="#" class="status" data-id="'
							+ item.productId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">Preview</a>'
							+ '</div>'
							+ '</div>';
				});
				// Assign the spliced information into the html control
				$('.product-wrap').html(tempHtml);
			}
		});
	}
	// Bind the click event to the a tag in product-wrap with class
	$('.product-wrap')
			.on(
					'click',
					'a',
					function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							// If there is class edit, click to enter the shop information editing page with the productId parameter.
							window.location.href = '/o2o/shopadmin/productoperation?productId='
									+ e.currentTarget.dataset.id;
						} else if (target.hasClass('status')) {
							// If there is class status, the background function is called to add/remove related products, with the productId parameter.
							changeItemStatus(e.currentTarget.dataset.id,
									e.currentTarget.dataset.status);
						} else if (target.hasClass('preview')) {
							// If there is a class preview, go to the front-end display system to preview the product details page.
							window.location.href = '/o2o/frontend/productdetail?productId='
									+ e.currentTarget.dataset.id;
						}
					});
	function changeItemStatus(id, enableStatus) {
		// Define the product json object and add productId and status (Online/Offline)
		var product = {};
		product.productId = id;
		product.enableStatus = enableStatus;
		$.confirm('Are you sure?', function() {
			// Offline related product
			$.ajax({
				url : statusUrl,
				type : 'POST',
				data : {
					productStr : JSON.stringify(product),
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('Operation success！');
						getList();
					} else {
						$.toast('Operation fail！');
					}
				}
			});
		});
	}
});