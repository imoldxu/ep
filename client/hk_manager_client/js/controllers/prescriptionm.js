'use strict';

/* Controllers */

  // PrescriptionmCtrl controller
app.controller('PrescriptionmCtrl', ['$scope','$http','$modal','$state','$stateParams', function($scope, $http, $modal, $state, $stateParams) {
    
	$scope.pList = [];
	$scope.hasNextPage = false;
	$scope.pageIndex = 1; 
	$scope.barcode =  '';
	$scope.doctorname =  '';
	$scope.startDate =  '';
	$scope.endDate =  '';
	
	$scope.nextPage = function(){
		$scope.pageIndex = $scope.pageIndex+1;
		$scope.getPrecriptionList();
	}
	
	$scope.prePage = function(){
		$scope.pageIndex = $scope.pageIndex-1;
		$scope.getPrecriptionList();
	}
	
	$scope.search = function() {
		
	  $scope.pageIndex = 1;
	  $scope.pList = [];
	  $scope.hasNextPage = false;
	
	  $scope.getPrecriptionList();
	}
	
	$scope.getPrecriptionList = function(){
      $http({
		  method: "GET",  
		  url: $scope.app.url_prescription+'prescription/getPrescriptionList',
		  params: {barcode: $scope.barcode || '',
				   doctorName: $scope.doctorname || '',
				   startDate: $scope.startDate || '',
				   endDate: $scope.endDate || '',
				   pageIndex: $scope.pageIndex,
				   pageSize: 20}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.pList = response.data;
			if($scope.pList.length == 20){
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
	
	$scope.show = function (index, size) {
	  $http({
		  method: "GET",  
		  url: $scope.app.url_prescription+'prescription/getPrescriptionByID',
		  params: {
			  pid: $scope.pList[index].id
		  }
	  })
      .success(function(response) {
        if (response.code == 1) {
			 var modalInstance = $modal.open({
				templateUrl: 'showPrescriptionModalContent.html',
				controller: 'ShowPrescriptionModalInstanceCtrl',
				size: size,
				resolve: {
				  prescription: function () {
					return response.data;
				  }
				}
			  });

			  modalInstance.result.then(function (data) {
			  }, function () {
				$log.info('Modal dismissed at: ' + new Date());
			  });
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
	
	$scope.$watch('$viewContentLoaded', function() {
		$scope.getPrecriptionList();
	});
  }])
 ;