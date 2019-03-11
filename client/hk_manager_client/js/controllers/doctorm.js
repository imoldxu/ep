'use strict';

/* Controllers */

  // DoctormCtrl controller
app.controller('DoctormCtrl', ['$scope','$http','$modal','$state','$stateParams','$log', function($scope, $http, $modal, $state, $stateParams, $log) {
    
	$scope.hospitalList = [];
	$scope.doctorList = [];
	$scope.hasNextPage = false;
	$scope.pageIndex = 1; 
	$scope.name = '';
	$scope.phone = '';
	$scope.selectedhospital = null;
	
	$scope.nextPage = function(){
		 $scope.pageIndex = $scope.pageIndex + 1;
		 $scope.getDoctorList();
	}
	
	$scope.prePage = function(){
		 $scope.pageIndex = $scope.pageIndex - 1;
		 $scope.getDoctorList();
	}
	
	$scope.searchDoctor = function() {
		
	  $scope.pageIndex = 1;
	  $scope.doctorList = [];
	  $scope.hasNextPage = false;
	
	  $scope.getDoctorList();
	}
	
	$scope.selectHospital = function(index){
		$scope.selectedHospital = $scope.hospitalList[index];
		$scope.pageIndex = 1;
	    $scope.doctorList = [];
	    $scope.hasNextPage = false;
		$scope.name = '';
		$scope.phone = '';
		$scope.getDoctorList();
		return false;
	}
	
	$scope.getDoctorList = function(){
	  $http({
		  method: "GET",
		  url: $scope.app.url_user+'doctor/getDoctors',
		  params: {hospitalid: $scope.selectedHospital.id,
				   name: $scope.name,
				   phone: $scope.phone,
				   pageIndex: 1,
				   pageSize: 20}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.doctorList = response.data;
			if(response.data == 20){
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
	
	
	$scope.getHospitalList = function(){
      $http({
		  method: "GET",  
		  url: $scope.app.url_user+'hospital/getHospitalsByName',
		  params: {name: $scope.name,
				   pageIndex: 1,
				   pageSize: 100}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.hospitalList = response.data;
			$scope.selectHospital(0);
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
        templateUrl: 'addDoctorModalContent.html',
        controller: 'AddDoctorModalInstanceCtrl',
        size: size,
        resolve: {
			hospital: function(){
				return angular.copy($scope.selectedHospital);
			}
		}
      });

      modalInstance.result.then(function (data) {
		$scope.doctorList.unshift(data);
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };
	
	$scope.edit = function (index, size) {
      var modalInstance = $modal.open({
        templateUrl: 'modifyDoctorModalContent.html',
        controller: 'ModifyDoctorModalInstanceCtrl',
        size: size,
        resolve: {
			doctor: function(){
				return angular.copy($scope.doctorList[index]);
			}
		}
      });

      modalInstance.result.then(function (data) {
		$scope.doctorList[index] = data;
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };
	
	
	$scope.delDoctor = function(index){
		$http({
		  method: "POST",
		  url: $scope.app.url_user+"doctor/del",
		  data: {
			  doctorid: $scope.doctorList[index].id
			}
		  })
		  .success(function(response){
			if (response.code == 1) {
				$scope.doctorList.splice(index, 1);
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