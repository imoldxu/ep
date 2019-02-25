/**
 * Created by chen on 2016/3/1.
 */
/**
 * 路由
 */
define(['app','angularAMD'], function(app,angularAMD){
    return app.config(['$stateProvider','$urlRouterProvider','$locationProvider',function($stateProvider, $urlRouterProvider,$locationProvider) {

        $urlRouterProvider
            .when('','/login');
        $stateProvider
		.state('login', angularAMD.route({
			url:"/login",
			templateUrl:'js/views/login.html?ver='+ver,
			controllerUrl: "js/controllers/loginCtrl.js"
		}))
        .state('home',angularAMD.route({
            url:"/home",
            templateUrl:'js/views/home.html?ver='+ver,
            controllerUrl: "js/controllers/homeCtrl.js"
        }))
		.state('prescription',angularAMD.route({
            url: '/prescription',
            templateUrl: 'js/views/prescription.html?ver='+ver,
            controllerUrl: "js/controllers/prescriptionCtrl.js"
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
		.state('register',angularAMD.route({
            url: '/register',
            templateUrl: 'js/views/register.html?ver='+ver,
            controllerUrl: "js/controllers/registerCtrl.js"
        }))
		.state('addPatient',angularAMD.route({
            url: '/addPatient',
            templateUrl: 'js/views/addPatient.html?ver='+ver,
            controllerUrl: "js/controllers/addPatientCtrl.js"
        }))
		.state('modifyPatient',angularAMD.route({
            url: '/modifyPatient/:patient',
            templateUrl: 'js/views/modifyPatient.html?ver='+ver,
            controllerUrl: "js/controllers/modifyPatientCtrl.js"
        }))
		.state('showPatientCode',angularAMD.route({
            url: '/showPatientCode/:code',
            templateUrl: 'js/views/showPatientCode.html?ver='+ver,
            controllerUrl: "js/controllers/showPatientCodeCtrl.js"
        }))
		.state('wxQrcode',angularAMD.route({
            url: '/wxQrcode',
            templateUrl: 'js/views/wxQrcode.html?ver='+ver,
            controllerUrl: "js/controllers/wxQrcodeCtrl.js"
        }))
		.state('article',angularAMD.route({
            url: '/article/:articleId',
            templateUrl: 'js/views/article.html?ver='+ver,
            controllerUrl: "js/controllers/articleCtrl.js"
        }))
    }])


});
