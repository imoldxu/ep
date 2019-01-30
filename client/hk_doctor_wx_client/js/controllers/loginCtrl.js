
define([], function(){

    return ['$scope', '$http', '$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

        //默认值

        $scope.login = function( phone, pwd){

            if (phone == '' || phone == undefined){
                alert('请输入登录手机号')
				return false;
            }
			if (pwd == '' || pwd == undefined){
                alert('请输入登录密码')
				return false;
            }
			
			//FIXME: 暂时屏蔽
			//pwd = Md5.b64_hmac_md5("hk",pwd);//使用md5对密码加密,并转换为HEX
			
			$rootScope.myloader = true;
			
            $http({
                method: 'post',
                url: URL+'doctor/login',
                data: {
                    phone: phone,
					password: pwd
                }
            })
            .success(function(resp){
				
				$rootScope.myloader = false;

                if (resp.code == 1){

                    dataVer.put('doctorInfo',resp.data);
					
					if(resp.data.signatureurl != null && resp.data.name != null && resp.data.department != null){
						$state.go('index');
					}else{
						$state.go('updateInfo');
					}
					
                    return false

                }else{

					alert(resp.msg);

					console.log(resp);
				}

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})
        }
		
		$scope.gotoRegister = function(){
			$state.go('register');
		}
    }];

});
