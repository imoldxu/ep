/**
 * Created by chen on 2016/2/26.
 * var URL = 'http://172.76.30.100:8866/';  // 彭州中心医院请求地址; 
 * var URL = 'http://172.76.108.242:8866/'; // 彭州中医院地址;
 * var URL = 'http://127.0.0.1:8866/';  // 调试请求地址;
 * var URL = 'http://120.77.73.224:8866/';  // 测试请求地址;
 */


var URL = 'http://127.0.0.1:8866/';  // 请求地址;

var ver = '1.0.15';


require.config({
    paths: {
        'angular': 'libs/angular',
        'jquery': 'libs/jquery',
        'ng-route':"libs/angular-ui-router",
        'ng-cookies': 'libs/angular-cookies',
        'fn-route':"route/route",
        "app" : "controllers/app",
        'angularAMD':'libs/angularAMD',
        'data':'services/data',
        'layer':'libs/layer/layer',
		'loader':'directives/loader'


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
        }
    },

    urlArgs: "ver="+ver


});



require(['jquery','angular','ng-route','app','fn-route','angularAMD','data','loader'],function($,angular){
    $(function () {
        angular.bootstrap(document,["fnApp"]);

    })
});