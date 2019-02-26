/**
 * Created by chen on 2016/2/26.
 * var URL = 'http://172.76.30.100:8866/';  // 彭州中心医院请求地址; 
 * var URL = 'http://172.76.108.242:8866/'; // 彭州中医院地址;
 * var URL = 'http://127.0.0.1:8866/';  // 调试请求地址;
 * var URL = 'http://120.77.73.224:8866/';  // 测试请求地址;
 */


//var URL = 'http://127.0.0.1:9201/';  // 请求地址;
//var URL2 = 'http://127.0.0.1:9202/'  // 药品服务
//var URL3 = 'http://127.0.0.1:9203/'  // 处方服务

//var URL = 'http://192.168.31.187:9201/';  // 请求地址;
//var URL2 = 'http://192.168.31.187:9202/'  // 药品服务
//var URL3 = 'http://192.168.31.187:9203/'  // 处方服务
//var URL4 = 'http://192.168.31.187:9204/'  // 系统服务
var URL = 'http://192.168.43.39:9201/';  // 请求地址;
var URL2 = 'http://192.168.43.39:9202/'  // 药品服务
var URL3 = 'http://192.168.43.39:9203/'  // 处方服务
var URL4 = 'http://192.168.43.39:9204/'  // 系统服务
var appServer = "http://192.168.43.39:9210";//OSS-STS

var ver = '1.0.7';


require.config({
    paths: {
        'angular': 'libs/angular',
        'jquery': 'libs/jquery',
        'ng-route':"libs/angular-ui-router",
        'ng-cookies': 'libs/angular-cookies',
        'fn-route':"route/route",
	    'io-barcode':'libs/angular-io-barcode',
        "app" : "controllers/app",
        'angularAMD':'libs/angularAMD',
        'data':'services/data',
        'layer':'libs/layer/layer',
	    'wheader':'directives/wheader',
		'pull2refresh':'libs/angular-pull-to-refresh',
		'weui':'libs/weui.min',
		'encrypt':'libs/encrypt',
		'qrcode':'libs/qrcode',
		'utf8-qrcode':'libs/qrcode_UTF8',
        'angular-qrcode':'libs/angular-qrcode',
		'signature_pad':'libs/signature_pad.min',
		'signature':'libs/signature',
		'oss': 'libs/aliyun-oss-sdk',
    'infiniteScroll':'libs/ng-infinite-scroll.min'
    },
    shim:{
        "angular":{
            exports: "angular"
        },
        'ng-route':{
            deps: ["angular"],
            exports: 'ng-route'
        },
        "angularAMD": ["angular"],
        'ng-cookies': ['angular'],
        "picturecut":{
            exports:"picturecut"       
	    },
		"encrypt": {
			deps: ["angular"]
		},
		"utf8-qrcode": {
			deps: ["qrcode"]
		},
		"angular-qrcode": {
			deps: ["angular","utf8-qrcode"]
		},
		"signature_pad": {
			deps: ["angular"]
		},
		"signature":{
			deps: ["angular","signature_pad"]
		},
    "infiniteScroll":{
			deps: ["angular"]
		},
    "pull2refresh":{
			deps: ["angular"]
		}
    },

    urlArgs: "ver="+ver


});



require(['jquery','angular','ng-route','ng-cookies','app','fn-route','angularAMD','data','weui','encrypt','io-barcode','qrcode','utf8-qrcode','angular-qrcode','infiniteScroll','wheader','pull2refresh','signature_pad','signature','oss'],function($,angular){
    $(function () {
        angular.bootstrap(document,["fnApp"]);
    })
});