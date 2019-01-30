
define([], function(){

    return ['$scope', '$http', '$window','$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

        //默认值
        $scope.registe = function(phone, pwd){

            if (phone == '' || phone == undefined){
                alert('请输入登录手机号')
				return false;
            }
			if (pwd == '' || pwd == undefined){
                alert('请设置登录密码')
				return false;
            }
			
			//FIXME: 暂时屏蔽
			//pwd = Md5.b64_hmac_md5("hk",pwd);//使用md5对密码加密,并转换为HEX
			
			$rootScope.myloader = true;
			
            $http({
                method: 'post',
                url: URL+'doctor/register',
                data: {
                    phone: phone,
					password: pwd
                }
            })
            .success(function(resp){
				
				$rootScope.myloader = false;

                if (resp.code == 1){

                    alert('注册成功');
					
					dataVer.put('doctorInfo', resp.data);
					
					$state.go('updateInfo');

                    return false

                } else{

					alert(resp.msg);

					console.log(resp)
				}

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})
        }
		
		$scope.goBack = function() {
			$window.history.back();
		}
    }];

});
