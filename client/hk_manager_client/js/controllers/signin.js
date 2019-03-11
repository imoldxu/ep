'use strict';

/* Controllers */
  // signin controller
app.controller('SigninFormController', ['$scope', '$http', '$localStorage','$state','myStorage', function($scope, $http, $localStorage, $state, myStorage) {
    $scope.user = {};
    $scope.authError = null;
    $scope.login = function() {
      $scope.authError = null;
      // Try to login
      $http({  
            method: "POST",  
            url: $scope.app.url_user+'manager/login',  
            data: {
				phone: $scope.user.phone,
				password: $scope.user.password
			}
	  })
      .success(function(response) {
        if (response.code != 1) {
          $scope.authError = response.msg;
        }else{
		  myStorage.put('auth.user', response.data);
		  $state.go('app.dashboard-v1');
        }
      })
	  .error(function(x) {
        $scope.authError = '系统错误';
      });
    };
  }])
;