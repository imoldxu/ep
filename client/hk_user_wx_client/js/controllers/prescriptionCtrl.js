
define(['angular','layer'], function(angular,layer){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window,$location,$rootScope,dataVer,$state){
		
        $scope.prescriptionObj = dataVer.get('prescriptionInfo');

        $scope.drugList = $scope.prescriptionObj.drugList;

		$scope.showBarcode = function(){
			$state.go('myBarcode',{code: $scope.prescriptionObj.barcode});
		}

        $scope.searchDrugs = function(){
			dataVer.put('locationInfo',null);//进入搜索页面总是以当前位置查找
		
			$state.go('result');
			/*
			var drugIds = [];
		
			for(var i = 0;i<$scope.drugList.length;i++){
				drugIds.push($scope.drugList[i].drugid);
			}
		
			$rootScope.myloader = true;
			
			$http({
				method: 'get',

				url: URL2+'store/getStoresByDrugs',

				requestType: 'json',

				params: {

					drugidListStr: JSON.stringify(drugIds),
					
					latitude: 30.9,
				
					longitude: 103.9,

					size: 10
				}
			})
			.success(function(resp){
				
				$rootScope.myloader = false;

				if (resp.code == 1){

					dataVer.put('resultInfo', resp.data);
					
					$state.go('result');
				}else if(resp.code == 4){
					alert(resp.msg);
					
					$state.go('login');
				}else{
				
					alert(resp.msg);

					console.log(resp);
				}
			
			})
			.error(function(data){
			
				$rootScope.myloader = false;
			
				alert('系统服务异常，请联系管理员');
			
			});
           */
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
    }];

});
