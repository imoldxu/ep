
define(['angular','layer','weui'], function(angular,layer,weui){

    return ['$scope', '$http', '$window', '$document','$location','$rootScope','dataVer' ,'$state','$stateParams', function($scope, $http, $window,$document,$location,$rootScope,dataVer,$state,$stateParams){

        //默认值
		$scope.prescriptionObj = dataVer.get('prescriptionInfo');

		$scope.drugList = $scope.prescriptionObj.drugList;
        
		$scope.location = dataVer.get('locationInfo') || {place:"点击选择你的位置", latitude:null, longitude:null};
		
        $scope.storeList = [];

		$scope.noneStoreDrugList = null;
	
		$scope.gotoPage = function(page){
			$state.go(page);
		}
		
		$scope.showBarcode = function(){
			//$state.go('myBarcode',{code: $scope.prescriptionObj.barcode});
			$window.history.back();
		}
		
		$scope.gotoNav = function(index){
			var addr ={
				latitude: $scope.storeList[index].latitude,
				longitude: $scope.storeList[index].longitude,
				addr: $scope.storeList[index].address,
				name: $scope.storeList[index].name
			}
			
			var addrStr = JSON.stringify(addr);
			$state.go('nav',{address:addrStr});
		}
		
		$scope.searchDrugs = function(){
		
			if( $scope.location.latitude == null || $scope.location.longitude == null){
				weui.alert('请选取你的位置');
				$state.go('map');
				return false;
			}
		
			var drugIds = [];
		
			for(var i = 0;i<$scope.drugList.length;i++){
				drugIds.push($scope.drugList[i].drugid);
			}
		
			var loading = weui.loading('正在玩命搜索...');
			
			$http({
				method: 'get',

				url: URL2+'store/getStoresByDrugs',

				requestType: 'json',

				params: {

					drugidListStr: JSON.stringify(drugIds),
					
					latitude: $scope.location.latitude,
				
					longitude: $scope.location.longitude,

					size: 10
				}
			})
			.success(function(resp){
				
				loading.hide();
				
				if (resp.code == 1){

					$scope.storeList = resp.data;
					
					if($scope.storeList.length > 0){
						if($scope.storeList[0].name == null){
							$scope.noneStoreDrugList = $scope.storeList[0].drugList;
							$scope.storeList.splice(0,1);
						}
					}
					
				}else if(resp.code == 4){
					weui.alert(resp.msg);
					
					$state.go('login');
				}else{
				
					weui.alert(resp.msg);

					console.log(resp);
				}
			
			})
			.error(function(data){
			
				loading.hide();
				
				weui.alert('系统服务异常，请联系管理员');
			
			});
           
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

		$scope.f2y = function( num ) {
			if ( typeof num !== "number" || isNaN( num ) ) return null;
			return ( num / 100 ).toFixed( 2 );
		}

		$scope.formatDistance = function(distance){
			if( distance < 1000){
				return distance+'米';
			}else{
				return (distance / 1000).toFixed ( 1 ) + '公里';
			}
		}

		$scope.$watch('$viewContentLoaded', function() {
			$scope.searchDrugs();
		}); 
		
    }];

});
