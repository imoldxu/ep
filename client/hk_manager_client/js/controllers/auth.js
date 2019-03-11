'use strict';

/* Controllers */
  // signin controller
app.controller('AuthFormController', ['$scope', '$http','$state','myStorage', function($scope, $http, $state, myStorage) {
      // Try to login
		$http({  
			method: "GET",  
			url: $scope.app.url_user+'manager/getInfo',  
			params: {}
		})
		.success(function(response) {
			if (response.code != 1) {
			  $state.go('access.signin');
			}else{
			  myStorage.put('auth.user', response.data);
			  $state.go('app.dashboard-v1');
			}
		})
		.error(function(x) {
			$state.go('access.signin');
		});
    
  }])
;