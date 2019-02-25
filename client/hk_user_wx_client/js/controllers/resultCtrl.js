
define(['angular','layer','weui'], function(angular,layer,weui){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window,$location,$rootScope,dataVer,$state){

        //默认值
		$scope.prescriptionObj = dataVer.get('prescriptionInfo');

        $scope.drugList = $scope.prescriptionObj.drugList;
		
		$scope.location = dataVer.get('locationInfo') || {place:"当前位置", latitude:30.9, longitude:103.9};
		
        $scope.storeList = [];

		$scope.noneStoreDrugList = null;
	
		$scope.gotoPage = function(page){
			$state.go(page);
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

		$scope.$watch('$viewContentLoaded', function() {
			// 页面加载完执行

			$window.wx.ready(function(){
				$window.wx.getLocation({
					type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
					success: function (res) {
						$scope.location.latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
						$scope.location.longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
						var speed = res.speed; // 速度，以米/每秒计
						$scope.location.place = res.accuracy; // 位置精度
									
						$scope.searchDrugs();
					}
				});
			});
			//在微信还不能使用的时候demo触发
			$scope.searchDrugs();
		}); 
		
    }];

});
