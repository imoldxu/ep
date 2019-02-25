
define([], function(){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state','$stateParams', function($scope, $http, $window,$location,$rootScope,dataVer,$state,$stateParams){
		
        $scope.code = $stateParams.code;

		$scope.gotoHome = function(){
			
			$location.path('/home').replace();			
			
		}
    }];

});
