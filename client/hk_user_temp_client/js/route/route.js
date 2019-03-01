/**
 * Created by chen on 2016/3/1.
 */
/**
 * 路由
 */
define(['app','angularAMD'], function(app,angularAMD){
    return app.config(['$stateProvider','$urlRouterProvider','$locationProvider',function($stateProvider, $urlRouterProvider,$locationProvider) {

        $urlRouterProvider
            .when('','/init');
        $stateProvider
		 .state('init',angularAMD.route({
            url: '/init/:barcode',
            templateUrl: 'js/views/init.html?ver='+ver,
            controllerUrl: "js/controllers/initCtrl.js"
        }))
        .state('result',angularAMD.route({
            url: '/result',
            templateUrl: 'js/views/result.html?ver='+ver,
            controllerUrl: "js/controllers/resultCtrl.js"
        }))
		.state('myBarcode',angularAMD.route({
            url: '/myBarcode/:code',
            templateUrl: 'js/views/myBarcode.html?ver='+ver,
            controllerUrl: "js/controllers/myBarcodeCtrl.js"
        }))
		.state('map',angularAMD.route({
            url: '/map',
            templateUrl: 'js/views/map.html?ver='+ver,
            controllerUrl: "js/controllers/mapCtrl.js"
        }))
		.state('nav',angularAMD.route({
            url: '/nav/:address',
            templateUrl: 'js/views/nav.html?ver='+ver,
            controllerUrl: "js/controllers/navCtrl.js"
        }))
    }])


});
