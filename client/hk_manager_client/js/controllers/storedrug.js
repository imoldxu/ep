'use strict';

/* Controllers */

  // StoremCtrl controller
app.controller('StoreDrugCtrl', ['$scope','$http','$modal','$state','$stateParams', function($scope, $http, $modal, $state, $stateParams) {
    
	$scope.storeList = [];
	$scope.drugList = [];
	$scope.storeDrugList;
	$scope.hasNextPage = false;
	$scope.pageIndex = 1; 
	$scope.name = '';
	$scope.keys = '';
	$scope.selectedStore = null;
	
	
	$scope.nextPage = function(){
		 $scope.pageIndex = $scope.pageIndex + 1;
		 $scope.getStoreDrugList();
	}
	
	$scope.prePage = function(){
		 $scope.pageIndex = $scope.pageIndex - 1;
		 $scope.getStoreDrugList();
	}
	
	$scope.searchDrug = function() {
		
	  $scope.pageIndex = 1;
	  $scope.storeDrugList = [];
	  $scope.hasNextPage = false;
	
	  $scope.getStoreDrugList();
	  $scope.getDrugList();
	}
	
	$scope.selectStore = function(index){
		$scope.selectedStore = $scope.storeList[index];
		$scope.keys = '';
		$scope.storeDrugList = [];
		$scope.pageIndex = 1;
		$scope.hasNextPage = false;
		$scope.getStoreDrugList();
	}
	
	$scope.getStoreDrugList = function(){
	  $http({
		  method: "GET",
		  url: $scope.app.url_drug+'store/getStoreDrugListByStore',
		  params: {storeid: $scope.selectedStore.id,
				   key: $scope.keys,
				   pageIndex: $scope.pageIndex,
				   pageSize: 20}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.storeDrugList = response.data;
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
	
	
	$scope.getStoreList = function(){
      $http({
		  method: "GET",  
		  url: $scope.app.url_user+'store/getStoresByName',
		  params: {name: $scope.name,
				   pageIndex: 1,
				   pageSize: 100}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.storeList = response.data;
			$scope.selectStore(0);
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
	
	/**系统不管价格设置，由药房自己设置
	$scope.add = function (index, size) {
      var modalInstance = $modal.open({
        templateUrl: 'addStoreDrugModalContent.html',
        controller: 'AddStoreDrugModalInstanceCtrl',
        size: size,
        resolve: {
			drug: $scope.drugList[index],
		}
      });

      modalInstance.result.then(function (data) {
		$scope.storeDrugList.unshift(data);
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };
	*/
	
	$scope.addStoreDrug = function(index){
		$http({
		  method: "POST",
		  url: $scope.app.url_drug+"store/add",
		  data: {
			  storeid: $scope.selectedStore.id,
			  drugid: $scope.drugList[index].id,
			  drugName: $scope.drugList[index].drugname,
			  standard: $scope.drugList[index].standard,
			  company: $scope.drugList[index].company,
			  price: 0, //不设置价格
			  settlementPrice: 0  
		  }
		  })
		  .success(function(response){
			if (response.code == 1) {
				$scope.storeDrugList.unshift(response.data);
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
	
	$scope.delStoreDrug = function(index){
		$http({
		  method: "POST",
		  url: $scope.app.url_drug+"store/del",
		  data: {
			  storeid: $scope.storeDrugList[index].storeid,
			  drugid: $scope.storeDrugList[index].drugid,
			  storeDrugId: $scope.storeDrugList[index].id
			}
		  })
		  .success(function(response){
			if (response.code == 1) {
				$scope.storeDrugList.splice(index, 1);
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
	
	$scope.f2y = function( num ) {
			if ( typeof num !== "number" || isNaN( num ) ) return null;
			return ( num / 100 ).toFixed( 2 );
		}
	
	$scope.$watch('$viewContentLoaded', function() {
		$scope.getStoreList();
	});
  }])
 ;