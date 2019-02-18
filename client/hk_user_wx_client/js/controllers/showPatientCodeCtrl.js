
define([], function(){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state', '$stateParams', function($scope, $http, $window,$location,$rootScope,dataVer,$state,$stateParams){
		
        $scope.code = $stateParams.code;

		$scope.goBack = function(){
			$window.history.back();
		}
    }];

});
