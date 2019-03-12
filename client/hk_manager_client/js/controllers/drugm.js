'use strict';

/* Controllers */

  // DrugmCtrl controller
app.controller('DrugmCtrl', ['$scope','$http','$modal','$state','$stateParams', '$log', function($scope, $http, $modal, $state, $stateParams, $log) {
    
	$scope.drugList = [];
	$scope.hasNextPage = false;
	$scope.pageIndex = 1; 
	$scope.keys = '';
	
	$scope.nextPage = function(){
		$scope.pageIndex = $scope.pageIndex+1;
		$scope.getDrugList();
	}
	
	$scope.prePage = function(){
		$scope.pageIndex = $scope.pageIndex-1;
		$scope.getDrugList();
	}
	
	$scope.search = function() {
		
	  $scope.pageIndex = 1;
	  $scope.drugList = [];
	  $scope.hasNextPage = false;
	
	  $scope.getDrugList();
	}
	
	$scope.getDrugList = function(){
      $http({
		  method: "GET",  
		  url: $scope.app.url_drug+'drug/getDrugListByKeys',
		  params: {keys: $scope.keys,
				   pageIndex: $scope.pageIndex,
				   pageSize: 20}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.drugList = response.data;
			if($scope.drugList.length == 20){
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
	
	$scope.upload = function(files){

		var file = files[0];

		var formdata = new FormData();

		formdata.append('file', file);

		var uploadElement= angular.element("#file")

		$http({
			method:'POST',
			url:$scope.app.url_drug+'drug/uploadByExcel',
			data: formdata,
			transformRequest:null,
			headers: { 'Content-Type': undefined }
		})
		.success( function ( response )
		{
			uploadElement[0].value = "";
			if (response.code == 1){
				alert("上传成功");
				$scope.drugList = response.data;
			}else if(response.code == 4){
				alert(response.msg);
				$state.go('signin');
			}else{
				alert(response.msg);
			}
			return false;
		})
		.error(function(data){
			uploadElement[0].value = "";
			alert('网络故障，请稍后再试');
			
		})


	}
	
	$scope.edit = function (index, size) {
      var modalInstance = $modal.open({
        templateUrl: 'modifyDrugModalContent.html',
        controller: 'ModifyDrugModalInstanceCtrl',
        size: size,
        resolve: {
          drug: function () {
            return angular.copy($scope.drugList[index]);
          }
        }
      });

      modalInstance.result.then(function (data) {
        $scope.drugList[index] = data;
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };
	
	$scope.del = function(index){
		$http({
		  method: "POST",
		  url: $scope.app.url_drug+"drug/delDrug",
		  data: {
			  drugid: $scope.drugList[index].id
		  }
	  })
	  .success(function(response){
		if (response.code == 1) {
			alert('删除成功');
			$scope.drugList.splice(index, 1);
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
		$scope.getDrugList();
	});
  }])
 ;