
define(['weui'], function(weui){

    return ['$scope', '$http', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值
		$scope.drugList = [];
		
		$scope.getMyDrugs = function(){

			var loading = weui.loading('加载中...');

            $http({
                method: 'get',
                url: URL2+'doctor/getDrugListByDoctor',
                requestType: 'json',
                params: {
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

		$scope.del = function(index){
			
			var loading = weui.loading('删除中...');
			
			$http({
                method: 'post',
                url: URL2+'doctor/delDoctorDrug',
                requestType: 'json',
                data: {
					drugid: $scope.drugList[index].id
                }
            })
			.success(function(resp){

				loading.hide();

                if (resp.code == 1){

					$scope.drugList.splice(index,1);
					
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
				
			});
			
		}
		
		$scope.header_right_function = function(){
			
			$state.go('addDrug');
		}
		
		$scope.$watch('$viewContentLoaded', function() {
			$scope.getMyDrugs();
		});
	   
    }];

});
