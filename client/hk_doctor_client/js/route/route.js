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
        .state('index',angularAMD.route({
            url:"/index",
            templateUrl:'js/views/index.html?ver='+ver,
            controllerUrl: "js/controllers/indexCtrl.js"
        }))
        .state('home',angularAMD.route({
            url: '/home',
            templateUrl: 'js/views/home.html?ver='+ver,
            controllerUrl: "js/controllers/homeCtrl.js"

        }))
		.state('zyhome',angularAMD.route({
            url: '/zyhome',
            templateUrl: 'js/views/zyhome.html?ver='+ver,
            controllerUrl: "js/controllers/zyhomeCtrl.js"

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


        .state('drug',angularAMD.route({
            url: '/drug',
            templateUrl: 'js/views/drug.html?ver='+ver,
            controllerUrl: "js/controllers/drugCtrl.js"

        }))

		.state('pm',angularAMD.route({
            url: '/pm',
            templateUrl: 'js/views/pm.html?ver='+ver,
            controllerUrl: "js/controllers/pmCtrl.js"

        }))
		
		.state('manager',angularAMD.route({
            url: '/manager',
            templateUrl: 'js/views/manager.html?ver='+ver,
            controllerUrl: "js/controllers/managerCtrl.js"

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
