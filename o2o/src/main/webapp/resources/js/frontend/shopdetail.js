$(function() {
	var loading = false;
	// Maximum number of items allowed to be returned by paging
	var maxItems = 20;
	// Default number of products returned on one page
	var pageSize = 3;
	// URL for listing products
	var listUrl = '/o2o/frontend/listproductsbyshop';
	// Default page number
	var pageNum = 1;
	// Get ShopId from the address bar
	var shopId = getQueryString('shopId');
	var productCategoryId = '';
	var productName = '';
	// Get the URL for the list of shop information and product category information.
	var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId=' + shopId;
	// Renders basic shop information and a list of product categories for searching.
	getSearchDivData();
	// Pre-loaded with 10 product information
	addItems(pageSize, pageNum);

	//Assigning the URL of the redemption gift to the a tag
	//$('#exchangelist').attr('href', '/o2o/frontend/awardlist?shopId=' + shopId);
	// Get a list of information of shop and product categories
	function getSearchDivData() {
		var url = searchDivUrl;
		$
			.getJSON(
				url,
				function(data) {
					if (data.success) {
						var shop = data.shop;
						$('#shop-cover-pic').attr('src', shop.shopImg);
						$('#shop-update-time').html(
							new Date(shop.lastEditTime)
								.Format("yyyy-MM-dd"));
						$('#shop-name').html(shop.shopName);
						$('#shop-desc').html(shop.shopDesc);
						$('#shop-addr').html(shop.shopAddr);
						$('#shop-phone').html(shop.phone);
						// Get the list of product categories of the shop returned by the backend.
						var productCategoryList = data.productCategoryList;
						var html = '';
						// Iterate through the product list and generate a tag that can be clicked to search for products under the corresponding product category.
						productCategoryList
							.map(function(item, index) {
								html += '<a href="#" class="button" data-product-search-id='
									+ item.productCategoryId
									+ '>'
									+ item.productCategoryName
									+ '</a>';
							});
						// Bind the product category <a> tag to the corresponding HTML component
						$('#shopdetail-button-div').html(html);
					}
				});
	}
	/**
	 * Get list of products information displayed in the paging
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @returns
	 */
	function addItems(pageSize, pageIndex) {
		// Splice out the URL of the query, assigning a null value removes the restriction of this condition by default, 
		// and having a value means querying according to this condition.
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
			+ pageSize + '&productCategoryId=' + productCategoryId
			+ '&productName=' + productName + '&shopId=' + shopId;
		// Set the loader, to avoid overloading
		loading = true;
		// Visit the backend to get the list of products under the corresponding query conditions
		$.getJSON(url, function(data) {
			if (data.success) {
				// Get the total number of products under the current query condition
				maxItems = data.count;
				var html = '';
				// Iterate through the list of products and splice out the collection of cards
				data.productList.map(function(item, index) {
					html += '' + '<div class="card" data-product-id='
						+ item.productId + '>'
						+ '<div class="card-header">' + item.productName
						+ '</div>' + '<div class="card-content">'
						+ '<div class="list-block media-list">' + '<ul>'
						+ '<li class="item-content">'
						+ '<div class="item-media">' + '<img src="'
						+ item.imgAddr + '" width="44">'
						+ '</div>' + '<div class="item-inner">'
						+ '<div class="item-subtitle">' + item.productDesc
						+ '</div>' + '</div>' + '</li>' + '</ul>'
						+ '</div>' + '</div>' + '<div class="card-footer">'
						+ '<p class="color-gray">'
						+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
						+ 'Update</p>' + '<span>View</span>' + '</div>'
						+ '</div>';
				});
				// Adding a collection of cards to the target HTML component
				$('.list-div').append(html);
				// Get the total number of cards that have been displayed so far, including the ones that have been loaded previously.
				var total = $('.list-div .card').length;
				// If the total number is the same as the total number listed according to this query, then stop loading from the backend.
				if (total >= maxItems) {
					// Hide Prompt
					$('.infinite-scroll-preloader').hide();
				} else {
					$('.infinite-scroll-preloader').show();
				}
				// Otherwise, add 1 to the page number and continue loading out new shops
				pageNum += 1;
				// End of loading, ready to load again
				loading = false;
				// Refresh the page to show the newly loaded shops
				$.refreshScroller();
			}
		});
	}

	// Automatic paging search by scrolling down the screen
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});
	// After selecting a new product category, reset the page number, clear the original product list, and search by the new category.
	$('#shopdetail-button-div').on(
		'click',
		'.button',
		function(e) {
			// Get shop category ID
			productCategoryId = e.target.dataset.productSearchId;
			if (productCategoryId) {
				// If another category has already been selected, remove its selection effect and select a new one instead.
				if ($(e.target).hasClass('button-fill')) {
					$(e.target).removeClass('button-fill');
					productCategoryId = '';
				} else {
					$(e.target).addClass('button-fill').siblings()
						.removeClass('button-fill');
				}
				$('.list-div').empty();
				pageNum = 1;
				addItems(pageSize, pageNum);
			}
		});
	// Click on an item's card to go to the item's detail page.
	$('.list-div').on(
		'click',
		'.card',
		function(e) {
			var productId = e.currentTarget.dataset.productId;
			window.location.href = '/o2o/frontend/productdetail?productId='
				+ productId;
		});
	// When the name of the product to be queried has changed, reset the page number, clear the original product list, and query according to the new name.
	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});
	// Click to open the right sidebar
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	$.init();
});
