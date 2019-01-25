/**
 * Created by chen on 2016/3/1.
 */
/**
 * 路由
 */
define(['app','angularAMD'], function(app,angularAMD){
    return app.config(['$stateProvider','$urlRouterProvider','$locationProvider',function($stateProvider, $urlRouterProvider,$locationProvider) {

        $urlRouterProvider
            .when('','/manager');
        $stateProvider
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
