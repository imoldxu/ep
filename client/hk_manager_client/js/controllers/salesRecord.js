'use strict';

/* Controllers */

  // PrescriptionmCtrl controller
app.controller('SalesRecordCtrl', ['$scope','$http','$modal','$state','$stateParams', function($scope, $http, $modal, $state, $stateParams) {
    
	$scope.recordList = [];
	$scope.hasNextPage = false;
	$scope.pageIndex = 1; 
	$scope.startDate =  '';
	$scope.endDate =  '';
	
	$scope.nextPage = function(){
		$scope.pageIndex = $scope.pageIndex+1;
		$scope.getSalesRecordList();
	}
	
	$scope.prePage = function(){
		$scope.pageIndex = $scope.pageIndex-1;
		$scope.getSalesRecordList();
	}
	
	$scope.search = function() {
		
	  $scope.pageIndex = 1;
	  $scope.recordList = [];
	  $scope.hasNextPage = false;
	
	  $scope.getSalesRecordList();
	}
	
	$scope.getSalesRecordList = function(){
      $http({
		  method: "GET",  
		  url: $scope.app.url_prescription+'record/getRecords',
		  params: {startDate: $scope.startDate || '',
				   endDate: $scope.endDate || '',
				   pageIndex: $scope.pageIndex,
				   pageSize: 20}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.recordList = response.data;
			if($scope.recordList.length == 20){
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
	
	$scope.f2y = function( num ) {
			if ( typeof num !== "number" || isNaN( num ) ) return null;
			return ( num / 100 ).toFixed( 2 );
		}
	
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
	
	$scope.$watch('$viewContentLoaded', function() {
		$scope.getSalesRecordList();
	});
  }])
 ;