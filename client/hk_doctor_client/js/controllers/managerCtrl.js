
define([], function(){

    return ['$scope', '$http', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值
		$scope.nextpage = function(page){
			
			dataVer.put('pList', '');
			
			dataVer.put('serachOpt', '');
			
			$state.go(page);
		}

    }];

});

