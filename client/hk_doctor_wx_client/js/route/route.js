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
            url: '/home',
            templateUrl: 'js/views/home.html?ver='+ver,
            controllerUrl: "js/controllers/homeCtrl.js"
        }))
        .state('print',angularAMD.route({
            url: '/print',
            templateUrl: 'js/views/print.html?ver='+ver,
            controllerUrl: "js/controllers/printCtrl.js"

        }))
		.state('zyprint',angularAMD.route({
            url: '/zyprint',
            templateUrl: 'js/views/zyprint.html?ver='+ver,
            controllerUrl: "js/controllers/printCtrl.js"

        }))
		.state('result',angularAMD.route({
            url: '/result',
            templateUrl: 'js/views/result.html?ver='+ver,
            controllerUrl: "js/controllers/resultCtrl.js"

        }))


        .state('register',angularAMD.route({
            url: '/drug',
            templateUrl: 'js/views/register.html?ver='+ver,
            controllerUrl: "js/controllers/registerCtrl.js"

        }))

		.state('updateInfo',angularAMD.route({
            url: '/updateInfo',
            templateUrl: 'js/views/updateInfo.html?ver='+ver,
            controllerUrl: "js/controllers/updateInfoCtrl.js"

        }))
		
		.state('signaturePad',angularAMD.route({
            url: '/signaturePad',
            templateUrl: 'js/views/signaturePad.html?ver='+ver,
            controllerUrl: "js/controllers/signaturePadCtrl.js"

        }))
		.state('modifyPwd',angularAMD.route({
            url: '/modifyPwd',
            templateUrl: 'js/views/modifyPwd.html?ver='+ver,
            controllerUrl: "js/controllers/modifyPwdCtrl.js"

        }))
		
		.state('mprint',angularAMD.route({
            url: '/mprint',
            templateUrl: 'js/views/mprint.html?ver='+ver,
            controllerUrl: "js/controllers/mprintCtrl.js"

        }))

		.state('mzyprint',angularAMD.route({
            url: '/mzyprint',
            templateUrl: 'js/views/mzyprint.html?ver='+ver,
            controllerUrl: "js/controllers/mprintCtrl.js"

        }))
		
    }])


});
