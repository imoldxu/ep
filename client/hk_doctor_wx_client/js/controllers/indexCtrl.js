
define([], function(){

    return ['$scope', '$http', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值
		$scope.doctorObj = dataVer.get('doctorInfo');

		$scope.gotoPage = function(page){
			$state.go(page);
		}
 
		$scope.logout = function(){
			
			$rootScope.myloader = true;
		
			$http({
                method: 'get',
                url: URL+'doctor/logout',
				requestType: 'json',
                params: { 
                }
            })
            .success(function(data){

				$rootScope.myloader = false;

                if (data.code == 1){

					dataVer.put('doctorInfo',{});
			
					$state.go('login');

                }else{
				
					alert(data.msg);
				
				}

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			});
		
		}
    }];

});
