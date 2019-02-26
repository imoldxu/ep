
define(['weui'], function(weui){

    return ['$scope', '$http', '$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

        //默认值
		$scope.phone = dataVer.get('dloginname');

		$scope.gotoPage = function(page){
			$state.go(page);
		}

        $scope.login = function( phone, pwd){

            if (phone == undefined || phone == ''){
                weui.topTips('请输入登录手机号', 3000);
				return false;
            }
			if (pwd == undefined || pwd == '' ){
                weui.topTips('请输入登录密码', 3000);
				return false;
            }
			
			//FIXME: 暂时屏蔽
			//pwd = Md5.b64_hmac_md5("hk",pwd);//使用md5对密码加密,并转换为HEX
			
			var loading = weui.loading("登陆中...");
			
            $http({
                method: 'post',
                url: URL+'doctor/login',
                data: {
                    phone: phone,
					password: pwd
                }
            })
            .success(function(resp){
				
				loading.hide();

                if (resp.code == 1){

					weui.toast('登陆成功', 3000);

					dataVer.put('dloginname', phone);

                    dataVer.put('doctorInfo',resp.data);
					
					if(resp.data.signatureurl != null && resp.data.name != null && resp.data.department != null){
						$location.path('/home').replace();
					}else{
						$state.go('updateInfo');
					}
					
                    return false

                }else{

					weui.alert(resp.msg);

					console.log(resp);
				}

            })
			.error(function(data){
				
				loading.hide();
				
				weui.alert('系统服务异常，请联系管理员');
				
			})
        }
		
		$scope.gotoRegister = function(){
			$state.go('register');
		}
    }];

});
