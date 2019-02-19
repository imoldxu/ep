
define(['weui'], function(weui){

    return ['$scope', '$http', '$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

		$scope.doctorObj = dataVer.get('doctorInfo');

		$scope.key = '';
		
		$scope.drugList = [];

		$scope.clear = function(){
			$scope.key = '';
		}

		$scope.searchDrug = function(key){

			if(key.length<2){
				return false;
			}

			var loading = weui.loading('加载中...');

            $http({
                method: 'get',
                url: URL2+'hospital/getDrugsByKeys',
                requestType: 'json',
                params: {
					hid: $scope.doctorObj.hospitalid,
                    keys: key,
					type: 1
                }
            })
			.success(function(resp){

				loading.hide();

                if (resp.code == 1){

					$scope.drugList = resp.data;
					
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

        };

		
		$scope.add = function(index){
			
			var drug = $scope.drugList[index];
			
			weui.confirm("是否添加到我的常用药品目录",function(){
				var loading = weui.loading("加载中...");
			
				$http({
					method: 'post',
					url: URL2+'doctor/addDoctorDrug',
					requestType: 'json',
					data: {
						drugid: drug.id
					}
				})
				.success(function(resp){
					
					loading.hide();

					if (resp.code == 1){

						weui.toast('添加成功',3000);
						
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
			}, function(){
				return false;
			});
		}
	   
    }];

});
