/**
 * Created by chen on 2016/2/26.
 * var URL = 'http://172.76.30.100:8866/';  // 彭州中心医院请求地址; 
 * var URL = 'http://172.76.108.242:8866/'; // 彭州中医院地址;
 * var URL = 'http://127.0.0.1:8866/';  // 调试请求地址;
 * var URL = 'http://120.77.73.224:8866/';  // 测试请求地址;
 */


var URL = 'http://127.0.0.1:8866/';  // 请求地址;
//var URL2 = 'http://127.0.0.1:9202/'  // 药品服务
//var URL3 = 'http://127.0.0.1:9203/'  // 处方服务

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
	    'loader':'directives/loader',
	    'encrypt':'libs/encrypt',
		'qrcode':'libs/qrcode',
		'utf8-qrcode':'libs/qrcode_UTF8',
        'angular-qrcode':'libs/angular-qrcode'
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
		}
    },

    urlArgs: "ver="+ver


});



require(['jquery','angular','ng-route','app','fn-route','angularAMD','data','loader','encrypt','io-barcode','qrcode','utf8-qrcode','angular-qrcode'],function($,angular){
    $(function () {
        angular.bootstrap(document,["fnApp"]);

    })
});