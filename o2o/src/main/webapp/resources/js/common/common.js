Date.prototype.Format = function(fmt) {
	var o = {
		"M+": this.getMonth() + 1, // Month
		"d+": this.getDate(), // Day
		"h+": this.getHours(), // Hour
		"m+": this.getMinutes(), // Minute
		"s+": this.getSeconds(), // Second
		"q+": Math.floor((this.getMonth() + 3) / 3), // Season
		"S": this.getMilliseconds() // Millisecond
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
			.substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
				: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
/**
 * 
 */
function changeVerifyCode(img) {
	img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return decodeURIComponent(r[2]);
	}
	return '';
}

/**
 * Get the ContextPath of the project in order to correct the image routing so that it can be displayed normally
 * @returns
 */
function getContextPath() {
	return "/o2o/";
}