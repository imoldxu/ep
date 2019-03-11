'use strict';

/* Controllers */

  // HospitalmCtrl controller
app.controller('HospitalmCtrl', ['$scope','$http','$modal','$state','$stateParams','$log', function($scope, $http, $modal, $state, $stateParams,$log) {
    
	$scope.hospitalList = [];
	$scope.hasNextPage = false;
	$scope.pageIndex = 1; 
	$scope.name = '';
	
	$scope.nextPage = function(){
		$scope.pageIndex = $scope.pageIndex+1;
		$scope.getHospitalList();
	}
	
	$scope.prePage = function(){
		$scope.pageIndex = $scope.pageIndex-1;
		$scope.getHospitalList();
	}
	
	$scope.search = function() {
		
	  $scope.pageIndex = 1;
	  $scope.hospitalList = [];
	  $scope.hasNextPage = false;
	
	  $scope.getHospitalList();
	}
	
	$scope.getHospitalList = function(){
      $http({
		  method: "GET",  
		  url: $scope.app.url_user+'hospital/getHospitalsByName',
		  params: {name: $scope.name,
				   pageIndex: $scope.pageIndex,
				   pageSize: 20}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.hospitalList = response.data;
			if($scope.hospitalList.length == 20){
				$scope.hasNextPage = true;
			}else{
				$scope.hasNextPage = false;
			}
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('signin');
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
        templateUrl: 'addHospitalModalContent.html',
        controller: 'AddHospitalModalInstanceCtrl',
        size: size,
        resolve: {
		}
      });

      modalInstance.result.then(function (data) {
        $scope.hospitalList = [];
		$scope.hospitalList.push(data);
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };
	
	$scope.edit = function (index, size) {
      var modalInstance = $modal.open({
        templateUrl: 'modifyHospitalModalContent.html',
        controller: 'ModifyHospitalModalInstanceCtrl',
        size: size,
        resolve: {
          hospital: function () {
            return angular.copy($scope.hospitalList[index]);
          },
		  index: function() {
			return index;
		  }
        }
      });

      modalInstance.result.then(function (data) {
        $scope.hospitalList[index] = data;
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };
	
	$scope.resetPwd = function(index){
		$http({
		  method: "POST",
		  url: $scope.app.url_user+"hospital/resetPwd",
		  data: {
			  hospitalid: $scope.hospitalList[index].id
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			alert('修改成功');
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('signin');
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
		$scope.getHospitalList();
	});
  }])
 ;