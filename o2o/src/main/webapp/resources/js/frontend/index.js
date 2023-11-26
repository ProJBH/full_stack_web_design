$(function() {
	// Define the URL to access the backend and obtain the headline list and first-level category list
	var url = '/o2o/frontend/listmainpageinfo';
	// Visit the backend to get the headline list and first-level category list
	$.getJSON(url, function(data) {
		if (data.success) {
			// Get the list of headlines passed from the background
			var headLineList = data.headLineList;
			var swiperHtml = '';
			// Traverse the headline list and splice the carousel image group
			headLineList.map(function(item, index) {
				swiperHtml += '' + '<div class="swiper-slide img-wrap">'
						+ '<a href="' + item.lineLink
						+ '" external><img class="banner-img" src="'
						+ getContextPath() + item.lineImg + '" alt="'
						+ item.lineName + '"></a>' + '</div>';
			});
			// Assign the carousel group to the front-end HTML control
			$('.swiper-wrapper').html(swiperHtml);
			// Set the carousel image rotation time to 3 seconds
			$(".swiper-container").swiper({
				autoplay : 3000,
				// Whether to automatically stop autoplay when the user operates on the carousel image
				autoplayDisableOnInteraction : false
			});
			// Get the list of major categories passed from the background
			var shopCategoryList = data.shopCategoryList;
			var categoryHtml = '';
			// Traverse the list of major categories and splice out two (col-50) categories in one row
			shopCategoryList.map(function(item, index) {
				categoryHtml += ''
						+ '<div class="col-50 shop-classify" data-category='
						+ item.shopCategoryId + '>' + '<div class="word">'
						+ '<p class="shop-title">' + item.shopCategoryName
						+ '</p>' + '<p class="shop-desc">'
						+ item.shopCategoryDesc + '</p>' + '</div>'
						+ '<div class="shop-classify-img-warp">'
						+ '<img class="shop-img" src="' + getContextPath()
						+ item.shopCategoryImg + '">' + '</div>' + '</div>';
			});
			// Assign the spliced categories to the front-end HTML control for display
			$('.row').html(categoryHtml);
		}
	});

	// If you click "My", the sidebar will be displayed
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});

	$('.row').on('click', '.shop-classify', function(e) {
		var shopCategoryId = e.currentTarget.dataset.category;
		var newUrl = '/o2o/frontend/shoplist?parentId=' + shopCategoryId;
		window.location.href = newUrl;
	});

});
