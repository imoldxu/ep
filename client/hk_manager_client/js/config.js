// config

var app =  
angular.module('app')
  .config(
    [        '$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
    function ($controllerProvider,   $compileProvider,   $filterProvider,   $provide) {
        
        // lazy controller, directive and service
        app.controller = $controllerProvider.register;
        app.directive  = $compileProvider.directive;
        app.filter     = $filterProvider.register;
        app.factory    = $provide.factory;
        app.service    = $provide.service;
        app.constant   = $provide.constant;
        app.value      = $provide.value;
    }
  ])
  .config(['$httpProvider', function($httpProvider) {
		$httpProvider.defaults.withCredentials = true;  //设置允许跨域传Cookie
		//更改 Content-Type
		$httpProvider.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=utf-8";
		$httpProvider.defaults.headers.post["Accept"] = "*/*";
		$httpProvider.defaults.transformRequest = function (data) { 
			//把JSON数据转换成字符串形式
			if (data !== undefined) { 
				return $.param(data);
			}
			return data;
			
			//var str = [];
			//for(var p in data){
			//	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(data[p]));
			//}
			//return str.join("&");
		 };
	}])
  .config(['$translateProvider', function($translateProvider){
    // Register a loader for the static files
    // So, the module will search missing translation tables under the specified urls.
    // Those urls are [prefix][langKey][suffix].
    $translateProvider.useStaticFilesLoader({
      prefix: 'l10n/',
      suffix: '.js'
    });
    // Tell the module what language to use by default
    $translateProvider.preferredLanguage('en');
    // Tell the module to store the language in the local storage
    $translateProvider.useLocalStorage();
  }]);