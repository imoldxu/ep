'use strict';

/**
 * Config for the router
 */
angular.module('app')
  .run(
    [          '$rootScope', '$state', '$stateParams',
      function ($rootScope,   $state,   $stateParams) {
          $rootScope.$state = $state;
          $rootScope.$stateParams = $stateParams;        
      }
    ]
  )
  .config(
    [          '$stateProvider', '$urlRouterProvider',
      function ($stateProvider,   $urlRouterProvider) {
          
          $urlRouterProvider
              .otherwise('/access/auth');
          $stateProvider
              .state('app', {
                  abstract: true,
                  url: '/app',
                  templateUrl: 'tpl/app.html'
              })
              .state('app.dashboard-v1', {
                  url: '/dashboard-v1',
                  templateUrl: 'tpl/app_dashboard_v1.html',
                  resolve: {
                    deps: ['$ocLazyLoad',
                      function( $ocLazyLoad ){
                        return $ocLazyLoad.load(['js/controllers/chart.js']);
                    }]
                  }
              })
              .state('app.dashboard-hospital', {
                  url: '/dashboard-v2',
                  template: '<div>开发中...</div>',
                  resolve: {
                    deps: ['$ocLazyLoad',
                      function( $ocLazyLoad ){
                        return $ocLazyLoad.load(['js/controllers/chart.js']);
                    }]
                  }
              })
			  .state('app.prescriptionm', {
                  url: '/prescriptionm',
                  templateUrl: 'tpl/prescriptionm.html',
				  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/prescriptionm.js');
                      }]
                  }
              })
			  .state('app.salesRecord', {
                  url: '/salesRecord',
                  templateUrl: 'tpl/salesRecord.html',
				  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/salesRecord.js');
                      }]
                  }
              })
			  .state('app.storeAccount', {
                  url: '/storeAccount',
                  templateUrl: 'tpl/storeAccount.html',
				  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/storeAccount.js');
                      }]
                  }
              })
			  .state('app.storem', {
                  url: '/storem',
                  templateUrl: 'tpl/storem.html',
				  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/storem.js');
                      }]
                  }
              })
			  .state('app.hospitalm', {
                  url: '/hospitalm',
                  templateUrl: 'tpl/hospitalm.html',
				  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/hospitalm.js');
                      }]
                  }
              })
			  .state('app.hospitaldrug', {
                  url: '/hospitaldrug',
                  templateUrl: 'tpl/hospitaldrug.html',
				  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/hospitaldrug.js');
                      }]
                  }
              })
			  .state('app.storedrug', {
                  url: '/storedrug',
                  templateUrl: 'tpl/storedrug.html',
				  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/storedrug.js');
                      }]
                  }
              })
			  .state('app.drugm', {
                  url: '/drugm',
                  templateUrl: 'tpl/drugm.html',
				  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/drugm.js');
                      }]
                  }
              })
			  .state('app.doctorm', {
                  url: '/doctorm',
                  templateUrl: 'tpl/doctorm.html',
				  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/doctorm.js');
                      }]
                  }
              })
			  .state('app.articlem', {
                  url: '/articlem',
                  templateUrl: 'tpl/articlem.html',
				  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/articlem.js');
                      }]
                  }
              })
              .state('lockme', {
                  url: '/lockme',
                  templateUrl: 'tpl/page_lockme.html'
              })
              .state('access', {
                  url: '/access',
                  template: '<div ui-view class="fade-in-right-big smooth"></div>'
              })
			  .state('access.auth', {
                  url: '/auth',
                  template: '<div ui-view class="fade-in-right-big smooth" ng-controller="AuthFormController"></div>',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/auth.js'] );
                      }]
                  }
              })
              .state('access.signin', {
                  url: '/signin',
                  templateUrl: 'tpl/page_signin.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/signin.js'] );
                      }]
                  }
              });
      }
    ]
  );