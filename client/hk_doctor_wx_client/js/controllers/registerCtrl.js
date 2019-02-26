
define(['weui'], function(weui){

    return ['$scope', '$http', '$window','$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

        //默认值
        $scope.registe = function(phone, pwd){

            if (phone == undefined || phone == ''){
                weui.topTips('请输入登录手机号', 3000);
				return false;
            }
			if (pwd == undefined || pwd == ''){
                weui.topTips('请设置登录密码', 3000);
				return false;
            }
			
			//FIXME: 暂时屏蔽
			//pwd = Md5.b64_hmac_md5("hk",pwd);//使用md5对密码加密,并转换为HEX
			
			var loading = weui.loading("注册中...");
			
            $http({
                method: 'post',
                url: URL+'doctor/register',
                data: {
                    phone: phone,
					password: pwd
                }
            })
            .success(function(resp){
				
				loading.hide();

                if (resp.code == 1){

                    weui.toast('注册成功',1000);
					
					dataVer.put('doctorInfo', resp.data);
					
					$location.path('updateInfo').replace();

                    return false;

                } else{

					weui.alert(resp.msg);

					console.log(resp)
				}

            })
			.error(function(data){
				
				loading.hide();
				
				weui.alert('系统服务异常，请联系管理员');
				
			})
        }
    }];

});
