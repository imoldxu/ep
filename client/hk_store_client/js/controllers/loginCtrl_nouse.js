
define([], function(){

    return ['$scope', '$http', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值


        $scope.login = function( type, num){

            if (num == '' || num == undefined){
                alert('请输入诊断号')
				return false;
            }
			//if (num != -1){
			//    alert('该功能暂未开放，敬请期待')
			//	return false;
			//}
			$rootScope.myloader = true;
			
            $http({
                method: 'get',
                url: URL+'prescition/getDiagnosis',
                requestType: 'json',
                params: {
                    diagnosisnum: num
                }
            })
            .success(function(data){
				
				$rootScope.myloader = false;

                if (data.code == 1){

                    dataVer.put('drugInfo','');

                    dataVer.put('userInfo',data.data);


                    if (data.data.doctorname || ''){


                        var docArray = {

                            doctorname : data.data.doctorname,

                            department : data.data.department

                        }

                        dataVer.put('docArray',docArray);

                    }

					if(type==1){
                        $state.go('home');
					}else{
					    $state.go('zyhome');
					}
                    return false

                }else{

					alert(data.msg);

					console.log(data)
				}

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})
        }
    }];

});
