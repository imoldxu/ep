/*
 * angular-qrcode
 * (c) 2017 Monospaced http://monospaced.com
 * License: MIT
 */

define(['angular','oss'],function(angular, oss){

	if (typeof module !== 'undefined' &&
		typeof exports !== 'undefined' &&
		module.exports === exports){
	  module.exports = 'angular.aliyun.oss';
	}

	var myoss = angular.module('angular.aliyun.oss', []);
	myoss.service('ossService',function(){
		var appServer = "http://39.108.253.177:9310";
		var bucket = 'zumeng';
		var region = 'oss-cn-shenzhen';

		console.log(oss);

		var urllib = oss.urllib;
		var Buffer = oss.Buffer;
		//var OSS = oss.Wrapper;
		//var STS = oss.STS;

		this.applyTokenDo = function(func, content) {
			var url = appServer;
			return urllib.request(url, {
				method: 'GET'
			}).then(function (result) {
				var creds = JSON.parse(result.data);
				var client = new oss({
					region: region,
					accessKeyId: creds.AccessKeyId,
					accessKeySecret: creds.AccessKeySecret,
					stsToken: creds.SecurityToken,
					bucket: bucket
				});
				
				var buffer = new oss.Buffer(content);
				return func(client, buffer);
			});
		}
	})

});