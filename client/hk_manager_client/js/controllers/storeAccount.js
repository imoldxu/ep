'use strict';

/* Controllers */

  // StoremCtrl controller
app.controller('StoreAccountCtrl', ['$scope','$http','$modal','$state','$stateParams', function($scope, $http, $modal, $state, $stateParams) {
    
	$scope.storeList = [];
	$scope.recordList;
	$scope.hasNextPage = false;
	$scope.pageIndex = 1; 
	$scope.selectedStore = null;
	
	
	$scope.nextPage = function(){
		 $scope.pageIndex = $scope.pageIndex + 1;
		 $scope.getStoreRecords();
	}
	
	$scope.prePage = function(){
		 $scope.pageIndex = $scope.pageIndex - 1;
		 $scope.getStoreRecords();
	}
	
	$scope.searchDrug = function() {
		
	  $scope.pageIndex = 1;
	  $scope.recordList = [];
	  $scope.hasNextPage = false;
	
	  $scope.getStoreRecords();
	}
	
	$scope.selectStore = function(index){
		$scope.selectedStore = $scope.storeList[index];
		$scope.pageIndex = 1;
	    $scope.recordList = [];
		$scope.startDate = '';
		$scope.endDate = '';
		$scope.hasNextPage = false;
		$scope.getStoreRecords();
	}
	
	$scope.getStoreRecords = function(){
	  $http({
		  method: "GET",
		  url: $scope.app.url_prescription+'account/getAccountRecordByStore',
		  params: {storeid: $scope.selectedStore.storeid,
				   startDate: $scope.startDate || '',
				   endDate: $scope.endDate || '',
				   pageIndex: 1,
				   pageSize: 20}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.recordList = response.data;
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
	
	$scope.updateAccount = function(index, size){
	  var modalInstance = $modal.open({
        templateUrl: 'updateStoreAccountModalContent.html',
        controller: 'updateStoreAccountModalInstanceCtrl',
        size: size,
        resolve: {
			store: function(){
				return $scope.storeList[index];
			}
		}
      });

      modalInstance.result.then(function (data) {
		$scope.storeList[index].balance = data.balance;  
		$scope.selectStore(index);
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };
	
	$scope.getStoreList = function(){
      $http({
		  method: "GET",  
		  url: $scope.app.url_prescription+'account/getAllStoreAccount',
		  params: {name: $scope.name || '',
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
	
	$scope.dateFormat = function(time, format){
			var t = new Date(time);
			var tf = function(i){return (i < 10 ? '0' : '') + i};
			return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
				switch(a){
					case 'yyyy':
						return tf(t.getFullYear());
						break;
					case 'MM':
						return tf(t.getMonth() + 1);
						break;
					case 'mm':
						return tf(t.getMinutes());
						break;
					case 'dd':
						return tf(t.getDate());
						break;
					case 'HH':
						return tf(t.getHours());
						break;
					case 'ss':
						return tf(t.getSeconds());
						break;
				}
			})
		};
	
	$scope.f2y = function( num ) {
			if ( typeof num !== "number" || isNaN( num ) ) return null;
			return ( num / 100 ).toFixed( 2 );
		}
	
	$scope.$watch('$viewContentLoaded', function() {
		$scope.getStoreList();
	});
  }])
 ;