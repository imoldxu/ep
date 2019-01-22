
define([], function(){

    return ['$scope', '$http', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值
		$scope.doctor = dataVer.get('doctorInfo');

        $scope.init = function(type, num){

            if (num == '' || num == undefined){
                num = -1;
            }
			//if (num != -1){
			//    alert('该功能暂未开放，敬请期待')
			//	return false;
			//}
			$rootScope.myloader = true;
			
            $http({
                method: 'get',
                url: URL+'prescition/init',
                requestType: 'json',
                params: {
                    hospitalNum: num
                }
            })
            .success(function(resp){
				
				$rootScope.myloader = false;

                if (resp.code == 1){

                    dataVer.put('drugList',[]);//重置drugList信息

                    dataVer.put('prescriptionInfo',resp.data);


                    if (resp.data.doctorname){
						
						//检查登录的医生与医院返回的信息是否一致
						if($scope.doctor.name != resp.data.doctorname){
							alert("该诊断号不是你的处方");
						
							return false;
						}

                    }

					if(type==1){
                        $state.go('home');
					}else{
					    $state.go('zyhome');
					}
                    return false

                }else{

					alert(resp.msg);

					console.log(resp)
				}

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})
        }
		
		$scope.logout = function(){
			
			dataVer.put('doctorInfo',{});
			
			$state.go('login');

		}
    }];

});
