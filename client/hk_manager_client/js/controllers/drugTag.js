'use strict';

/* Controllers */

  // DrugmCtrl controller
app.controller('DrugTagCtrl', ['$scope','$http','$modal','$state','$stateParams', '$log', function($scope, $http, $modal, $state, $stateParams, $log) {
    
	$scope.drugList = [];
	$scope.tagList = [];
	$scope.keys = '';
	
	$scope.getDrugList = function(){
      $http({
		  method: "GET",  
		  url: $scope.app.url_drug+'drug/getDrugListByKeys',
		  params: {keys: $scope.keys,
				   pageIndex: $scope.pageIndex,
				   pageSize: 100}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.drugList = response.data;
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
	
	$scope.selectDrug = function(index){
		$scope.selectedDrug = $scope.drugList[index];
		$scope.getAllTagsByDrug();
	}
	
	$scope.getAllTagsByDrug = function(){

		$http({
			method:'GET',
			url:$scope.app.url_drug+'drug/getAllTagsByDrug',
			params: {
				drugid: $scope.selectedDrug.id
			}
		})
		.success( function ( response )
		{
			if (response.code == 1){
				$scope.tagList = response.data;
			}else if(response.code == 4){
				alert(response.msg);
				$state.go('access.signin');
			}else{
				alert(response.msg);
			}
			return false;
		})
		.error(function(data){
			alert('网络故障，请稍后再试');
		})
	}
	
	$scope.changeState = function (index) {
        var tag = $scope.tagList[index]; 
		if(tag.selected == 1){
			$http({
				method:'POST',
				url:$scope.app.url_drug+'drug/addTag',
				params: {
					drugid: $scope.selectedDrug.id,
					tagid: tag.tagid
				}
			})
			.success( function ( response )
			{
				if (response.code == 1){
					$scope.tagList[index].drugid = $scope.selectedDrug.id;
				}else if(response.code == 4){
					alert(response.msg);
					$state.go('access.signin');
				}else{
					alert(response.msg);
				}
				return false;
			})
			.error(function(data){
				alert('网络故障，请稍后再试');
			})
		}else if(tag.selected == 0){
			$http({
				method:'POST',
				url:$scope.app.url_drug+'drug/delTag',
				params: {
					drugid: tag.drugid,
					tagid: tag.tagid
				}
			})
			.success( function ( response )
			{
				if (response.code == 1){
					$scope.tagList[index].drugid = null;
				}else if(response.code == 4){
					alert(response.msg);
					$state.go('access.signin');
				}else{
					alert(response.msg);
				}
				return false;
			})
			.error(function(data){
				alert('网络故障，请稍后再试');
			})
		}
	}
	
	$scope.$watch('$viewContentLoaded', function() {
		$scope.getDrugList();
	});
  }])
 ;