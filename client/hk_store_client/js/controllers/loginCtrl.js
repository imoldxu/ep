
define([], function(){

    return ['$scope', '$http', '$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

        //默认值


        $scope.login = function( email, pwd){

            if (email == '' || email == undefined){
                alert('请输入登录邮箱')
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
                url: URL+'store/login',
                requestType: 'json',
                data: {
                    email: email,
					password: pwd
                }
            })
            .success(function(data){
				
				$rootScope.myloader = false;

                if (data.code == 1){

                    //dataVer.put('drugInfo','');

                    dataVer.put('storeInfo',data.data);
					
					$state.go('index');

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
