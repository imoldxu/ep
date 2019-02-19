
define(['weui'], function(weui){

    return ['$scope', '$http', '$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

        //默认值
		$scope.state = dataVer.get('pliststate') || {pageIndex:1, pList:[], isfinish: false, isloading: true} //初始化
		
		$scope.loadMore = function(){

			$scope.state.isloading = true;

            $http({
                method: 'get',
                url: URL3+'prescription/getDoctorPrescriptionList',
                requestType: 'json',
                params: {
                    pageIndex: $scope.state.pageIndex,
					pageSize: 10
                }
            })
			.success(function(resp){

				$scope.state.isloading = false;

                if (resp.code == 1){

					if(resp.data.length != 10){
						$scope.state.isfinish = true;
					}

					$scope.state.pageIndex = $scope.state.pageIndex + 1; 

					$scope.state.pList = $scope.state.pList.concat(resp.data);
					
                } else if(resp.code == 4){
					weui.alert(resp.msg);
					
					$state.go('login');
				} else{
					weui.alert(resp.msg);
				}

            })
			.error(function(data){
				
				$scope.state.isloading = false;
				
				weui.alert('系统服务异常，请联系管理员');
				
			})

        };

		
		$scope.gotoDetail = function(pid){
			
			var loading = weui.loading("加载中...");
			
			$http({
                method: 'get',
                url: URL3+'prescription/getDoctorPrescriptionByID',
                requestType: 'json',
                params: {
                    pid: pid
                }
            })
			.success(function(resp){
				
				loading.hide();

                if (resp.code == 1){

                    var prescription = resp.data;
					
					dataVer.put('pliststate', $scope.state);
					
					//dataVer.put('prescriptionInfo', prescription);
					var pdetail = JSON.stringify(prescription);
					
					$state.go('prescription',{presdetail: pdetail});
                } else if(resp.code == 4){
					weui.alert(resp.msg);
					
					$state.go('login');
				} else{
					weui.alert(resp.msg);
				}

            })
			.error(function(data){
				loading.hide();
				
				weui.alert('系统服务异常，请联系管理员');
				
			})
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
