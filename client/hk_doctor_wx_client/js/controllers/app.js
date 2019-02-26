/**
 * 建立angular.module
 */


define(['angular'], function (angular) {

    var app = angular.module('fnApp', ['ui.router','ngCookies','Encrypt','monospaced.qrcode','io-barcode','signature','infinite-scroll','mgcrea.pullToRefresh'])
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

		$http({
			url: URL4+"wx/config",
			method:"get",
			requestType: 'json',
			params: {
				url: $location.absUrl().split('#')[0]
			}
		})
		.success(function(resp){
			$window.wx.config({
				debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				appId: resp.data.appId, // 必填，公众号的唯一标识
				timestamp: resp.data.timestamp, // 必填，生成签名的时间戳
				nonceStr: resp.data.nonceStr, // 必填，生成签名的随机串
				signature: resp.data.signature,// 必填，签名，见附录1
				jsApiList: [
					'updateAppMessageShareData',
					'updateTimelineShareData',
					'onMenuShareWeibo',
					'onMenuShareQZone',
					'startRecord',
					'stopRecord',
					'onVoiceRecordEnd',
					'playVoice',
					'pauseVoice',
					'stopVoice',
					'onVoicePlayEnd',
					'uploadVoice',
					'downloadVoice',
					'chooseImage',
					'previewImage',
					'uploadImage',
					'downloadImage',
					'translateVoice',
					'getNetworkType',
					'openLocation',
					'getLocation',
					'hideOptionMenu',
					'showOptionMenu',
					'hideMenuItems',
					'showMenuItems',
					'hideAllNonBaseMenuItem',
					'showAllNonBaseMenuItem',
					'closeWindow',
					'scanQRCode',
					'chooseWXPay',
					'openProductSpecificView',
					'addCard',
					'chooseCard',
					'openCard',
				]// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			});
;
			console.log('微信sdk初始化成功');
		})
		.error(function(resp){
			console.log('微信sdk初始化失败');
		});

    });

    return app;

});
