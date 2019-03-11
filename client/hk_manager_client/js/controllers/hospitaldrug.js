'use strict';

/* Controllers */

  // HospitalDrugCtrl controller
app.controller('HospitalDrugCtrl', ['$scope','$http','$modal','$state','$stateParams', function($scope, $http, $modal, $state, $stateParams) {
    
	$scope.hospitalList = [];
	$scope.drugList = [];
	$scope.hospitalDrugList;
	$scope.hasNextPage = false;
	$scope.pageIndex = 1; 
	$scope.name = '';
	$scope.keys = '';
	$scope.selectedhospital = null;
	
	
	$scope.nextPage = function(){
		 $scope.pageIndex = $scope.pageIndex + 1;
		 $scope.getHospitalDrugList();
	}
	
	$scope.prePage = function(){
		 $scope.pageIndex = $scope.pageIndex - 1;
		 $scope.getHospitalDrugList();
	}
	
	$scope.searchDrug = function() {
		
	  $scope.pageIndex = 1;
	  $scope.hospitalDrugList = [];
	  $scope.hasNextPage = false;
	
	  $scope.getHospitalDrugList();
	  $scope.getDrugList();
	}
	
	$scope.selectHospital = function(index){
		$scope.selectedHospital = $scope.hospitalList[index];
		$scope.pageIndex = 1;
		$scope.hospitalDrugList = [];
		$scope.hasNextPage = false;
		$scope.keys =  '';
		$scope.getHospitalDrugList();
	}
	
	$scope.getHospitalDrugList = function(){
	  $http({
		  method: "GET",
		  url: $scope.app.url_drug+'hospital/getHospitalDrugsByHospital',
		  params: {hospitalid: $scope.selectedHospital.id,
				   key: $scope.keys,
				   pageIndex: 1,
				   pageSize: 20}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.hospitalDrugList = response.data;
			if(response.data == 20){
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
	
	$scope.getDrugList = function(){
      $http({
		  method: "GET",  
		  url: $scope.app.url_drug+'drug/getDrugListByKeys',
		  params: {keys: $scope.keys,
				   pageIndex: 1,
				   pageSize: 100}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.drugList = response.data;
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
	
	$scope.edit = function (index, size) {
      var modalInstance = $modal.open({
        templateUrl: 'modifyHospitalDrugModalContent.html',
        controller: 'ModifyHospitalDrugModalInstanceCtrl',
        size: size,
        resolve: {
			drug: function(){
				return angular.copy($scope.hospitalDrugList[index]);
			}
		}
      });

      modalInstance.result.then(function (data) {
		$scope.hospitalDrugList[index] = data;
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };

	
	$scope.addHospitalDrug = function(index){
		$http({
		  method: "POST",
		  url: $scope.app.url_drug+"hospital/add",
		  data: {
			  hospitalid: $scope.selectedHospital.id,
			  hospitalname: $scope.selectedHospital.name,
			  drugid: $scope.drugList[index].id,
			  drugname: $scope.drugList[index].drugname,
			  standard: $scope.drugList[index].standard,
			  company: $scope.drugList[index].company,
			  exid: 0 //暂不设置扩展
			}
		  })
		  .success(function(response){
			if (response.code == 1) {
				$scope.hospitalDrugList.unshift(response.data);
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
	
	$scope.delHospitalDrug = function(index){
		$http({
		  method: "POST",
		  url: $scope.app.url_drug+"hospital/del",
		  data: {
			  hospitalid: $scope.hospitalDrugList[index].hospitalid,
			  drugid: $scope.hospitalDrugList[index].drugid,
			  hospitalDrugId: $scope.hospitalDrugList[index].id
			}
		  })
		  .success(function(response){
			if (response.code == 1) {
				$scope.hospitalDrugList.splice(index, 1);
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