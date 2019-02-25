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
        .state('pList',angularAMD.route({
            url: '/pList',
            templateUrl: 'js/views/pList.html?ver='+ver,
            controllerUrl: "js/controllers/pListCtrl.js"

        }))
		.state('prescription',angularAMD.route({
            url: '/prescription/:presdetail',
            templateUrl: 'js/views/prescription.html?ver='+ver,
            controllerUrl: "js/controllers/prescriptionCtrl.js"
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
		.state('mydrugs',angularAMD.route({
            url: '/mydrugs',
            templateUrl: 'js/views/mydrugs.html?ver='+ver,
            controllerUrl: "js/controllers/mydrugsCtrl.js"

        }))
		.state('addDrug',angularAMD.route({
            url: '/addDrug',
            templateUrl: 'js/views/addDrug.html?ver='+ver,
            controllerUrl: "js/controllers/addDrugCtrl.js"

        }))
		.state('result',angularAMD.route({
            url: '/result',
            templateUrl: 'js/views/result.html?ver='+ver,
            controllerUrl: "js/controllers/resultCtrl.js"

        }))
		.state('forgetPwd',angularAMD.route({
            url: '/forgetPwd',
            templateUrl: 'js/views/forgetPwd.html?ver='+ver,
            controllerUrl: "js/controllers/forgetPwdCtrl.js"

        }))
		.state('resetPwd',angularAMD.route({
            url: '/resetPwd/:authInfo',
            templateUrl: 'js/views/resetPwd.html?ver='+ver,
            controllerUrl: "js/controllers/resetPwdCtrl.js"

        }))
		.state('verifyCode',angularAMD.route({
            url: '/verifyCode/:verifyInfo',
            templateUrl: 'js/views/verifyCode.html?ver='+ver,
            controllerUrl: "js/controllers/verifyCodeCtrl.js"
        }))
		.state('modifyPhone',angularAMD.route({
            url: '/modifyPhone',
            templateUrl: 'js/views/modifyPhone.html?ver='+ver,
            controllerUrl: "js/controllers/modifyPhoneCtrl.js"
        }))
    }])


});
