$(function() {
	var loading = false;
	// Maximum number of items allowed to be returned by paging, above which access to the backend is disabled
	var maxItems = 999;
	// Maximum number of items returned on one page
	var pageSize = 3;
	// URL for getting shop list 
	var listUrl = '/o2o/frontend/listshops';
	// Get the URLs for the shop category list and the region list
	var searchDivUrl = '/o2o/frontend/listshopspageinfo';
	// Pagination
	var pageNum = 1;
	// Try to get the parent shop category id from the address bar URL.
	var parentId = getQueryString('parentId');
	// Whether subclasses are selected
	var selectedParent = false;
	if (parentId) {
		selectedParent = true;
	}
	var regionId = '';
	var shopCategoryId = '';
	var shopName = '';
	// Renders a list of shop categories and a list of regions for searching.
	getSearchDivData();
	// Pre-load 10 shop information
	addItems(pageSize, pageNum);
	/**
	 * Get shop category list and region list information
	 * 
	 * @returns
	 */
	function getSearchDivData() {
		// If parentId is passed in, take out all the secondary categories below this primary category
		var url = searchDivUrl + '?' + 'parentId=' + parentId;
		$
			.getJSON(
				url,
				function(data) {
					if (data.success) {
						// Get the list of shop categories returned from the backend
						var shopCategoryList = data.shopCategoryList;
						var html = '';
						html += '<a href="#" class="button" data-category-id=""> All categories  </a>';
						// Iterate through the list of shop categories and splice out the a tag set
						shopCategoryList
							.map(function(item, index) {
								html += '<a href="#" class="button" data-category-id='
									+ item.shopCategoryId
									+ '>'
									+ item.shopCategoryName
									+ '</a>';
							});
						// Embed the stitched category tags into the frontend html component
						$('#shoplist-search-div').html(html);
						var selectOptions = '<option value="">All regions</option>';
						// Get the list of region information returned from the backend
						var regionList = data.regionList;
						// Iterate through the list of region information and splice out the set of option tags
						regionList.map(function(item, index) {
							selectOptions += '<option value="'
								+ item.regionId + '">'
								+ item.regionName + '</option>';
						});
						// Adding a tag set to an region list
						$('#region-search').html(selectOptions);
					}
				});
	}

	/**
	 * Get information about the list of shops displayed on a page
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @returns
	 */
	function addItems(pageSize, pageIndex) {
		// Splice out the URL of the query, assigning a null value removes the restriction of this condition by default, 
		// and having a value means querying according to this condition.
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
			+ pageSize + '&parentId=' + parentId + '&regionId=' + regionId
			+ '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
		// Set the loader, if you still fetch data in the background, you can not access the background again, to avoid multiple reloading
		loading = true;
		// Visit the backend to get the list of shops under the corresponding query conditions
		$.getJSON(url, function(data) {
			if (data.success) {
				// Get the total number of shops under the current query condition
				maxItems = data.count;
				var html = '';
				// Iterate through the list of stores and stitch together a collection of cards
				data.shopList.map(function(item, index) {
					html += '' + '<div class="card" data-shop-id="'
						+ item.shopId + '">' + '<div class="card-header">'
						+ item.shopName + '</div>'
						+ '<div class="card-content">'
						+ '<div class="list-block media-list">' + '<ul>'
						+ '<li class="item-content">'
						+ '<div class="item-media">' + '<img src="'
						+ item.shopImg + '" width="44">' + '</div>'
						+ '<div class="item-inner">'
						+ '<div class="item-subtitle">' + item.shopDesc
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
				// If the total number is the same as the total number listed according to this query, then stop loading in the background.
				if (total >= maxItems) {
					// Hide prompt
					$('.infinite-scroll-preloader').hide();
					return;
				} else {
					$('.infinite-scroll-preloader').show();
				}
				// Otherwise, add 1 to the page number and continue loading out the new store
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

	// Click on a shop's card to go to that shop's detail page
	$('.shop-list').on('click', '.card', function(e) {
		var shopId = e.currentTarget.dataset.shopId;
		window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
	});

	// After selecting a new shop category, 
	// reset the page number, clear the original shop list, and search the shops by the new category.
	$('#shoplist-search-div').on(
		'click',
		'.button',
		function(e) {
			if (parentId && selectedParent) {// If the pass-through is a subclass under a parent class
				shopCategoryId = e.target.dataset.categoryId;
				// If another category has already been selected, remove its selection effect and select a new one instead.
				if ($(e.target).hasClass('button-fill')) {
					$(e.target).removeClass('button-fill');
					shopCategoryId = '';
				} else {
					$(e.target).addClass('button-fill').siblings()
						.removeClass('button-fill');
				}
				// Since the query conditions have changed, empty the shop list and then make a query.
				$('.list-div').empty();
				// Reset page number
				pageNum = 1;
				addItems(pageSize, pageNum);
			} else {// If the passed-in parent class is null, the query follows the parent class
				parentId = e.target.dataset.categoryId;
				if ($(e.target).hasClass('button-fill')) {
					$(e.target).removeClass('button-fill');
					parentId = '';
				} else {
					$(e.target).addClass('button-fill').siblings()
						.removeClass('button-fill');
				}
				// Since the query conditions have changed, empty the shop list and then make a query.
				$('.list-div').empty();
				// Reset page number
				pageNum = 1;
				addItems(pageSize, pageNum);
			}

		});

	// When the name of the shop to be searched has changed, reset the page number, clear the original shop list, and search according to the new name.
	$('#search').on('change', function(e) {
		shopName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	// When the region information changes, reset the page number, clear the original shop list, and search by the new region
	$('#region-search').on('change', function() {
		regionId = $('#region-search').val();
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	// Click to open the right sidebar
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});

	// Initializing the page
	$.init();
});
