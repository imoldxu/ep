
define(['weui'], function(weui){

    return ['$scope', '$http', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值
		$scope.doctorObj = dataVer.get('doctorInfo');

		$scope.gotoPage = function(page){
			$state.go(page);
		}
 
		$scope.logout = function(){
			
			//$rootScope.myloader = true;
			var loading = weui.loading('加载中...');
		
			$http({
                method: 'get',
                url: URL+'doctor/logout',
				requestType: 'json',
                params: { 
                }
            })
            .success(function(data){

				//$rootScope.myloader = false;
				loading.hide();

                if (data.code == 1){

					dataVer.put('doctorInfo',{});
			
					$state.go('login');

                }else{
				
					weui.alert(data.msg);
				
				}

            })
			.error(function(data){
				
				//$rootScope.myloader = false;
				loading.hide();
				
				weui.alert('系统服务异常，请联系管理员');
				
			});
		
		}
    }];

});
