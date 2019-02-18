/**
 * 建立angular.module
 */


define(['angular'], function (angular) {

    var app = angular.module('fnApp', ['ui.router','ngCookies','Encrypt','monospaced.qrcode','io-barcode','infinite-scroll'])
	.config(['$httpProvider', function($httpProvider) {
		$httpProvider.defaults.withCredentials = true;  //设置允许跨域传Cookie
		//更改 Content-Type
		$httpProvider.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=utf-8";
		$httpProvider.defaults.headers.post["Accept"] = "*/*";
		$httpProvider.defaults.transformRequest = function (data) { 
			//把JSON数据转换成字符串形式
			if (data !== undefined) { 
				return $.param(data);
			}
			return data;
			
			//var str = [];
			//for(var p in data){
			//	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(data[p]));
			//}
			//return str.join("&");
		 };
	}])
    .run(function($rootScope,$location,$window,$state,$http) {

        $rootScope.cssVer = '1.0.3';

    });

    return app;

});
