'use strict';

/* Controllers */

  // StoremCtrl controller
app.controller('StoremCtrl', ['$scope','$http','$modal','$state','$stateParams','$log', function($scope, $http, $modal, $state, $stateParams,$log) {
    
	$scope.storeList = [];
	$scope.hasNextPage = false;
	$scope.pageIndex = 1; 
	$scope.name = '';
	
	$scope.nextPage = function(){
		$scope.pageIndex = $scope.pageIndex+1;
		$scope.search();
	}
	
	$scope.prePage = function(){
		$scope.pageIndex = $scope.pageIndex-1;
		$scope.search();
	}
	
	$scope.search = function() {
		
	  $scope.pageIndex = 1;
	  $scope.storeList = [];
	  $scope.hasNextPage = false;
	
	  $scope.getStoreList();
	}
	
	$scope.getStoreList = function(){
      $http({
		  method: "GET",  
		  url: $scope.app.url_user+'store/getStoresByName',
		  params: {name: $scope.name,
				   pageIndex: $scope.pageIndex,
				   pageSize: 20}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.storeList = response.data;
			if($scope.storeList.length == 20){
				$scope.hasNextPage = true;
			}else{
				$scope.hasNextPage = false;
			}
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
      })
	  .error(function(x) {
            alert('网络异常,请稍后再试');
      });
    };
	
	$scope.add = function (size) {
      var modalInstance = $modal.open({
        templateUrl: 'addStoreModalContent.html',
        controller: 'AddStoreModalInstanceCtrl',
        size: size,
        resolve: {}
      });

      modalInstance.result.then(function (data) {
        $scope.storeList = [];
		$scope.storeList.push(data);
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };
	
	$scope.edit = function (index, size) {
      var modalInstance = $modal.open({
        templateUrl: 'modifyStoreModalContent.html',
        controller: 'ModifyStoreModalInstanceCtrl',
        size: size,
        resolve: {
          store: function () {
            return angular.copy($scope.storeList[index]);
          },
		  index: function() {
			return index;
		  }
        }
      });

      modalInstance.result.then(function (data) {
        $scope.storeList[index] = data;
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };
	
	$scope.resetPwd = function(index){
		$http({
		  method: "POST",
		  url: $scope.app.url_user+"store/resetPwd",
		  data: {
			  storeid: $scope.storeList[index].id
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			alert('修改成功');
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
	  })
	  .error(function(data){
		  alert('网络异常，请稍后再试');
	  });
	}
	
	$scope.$watch('$viewContentLoaded', function() {
		$scope.getStoreList();
	});
  }])
 ;