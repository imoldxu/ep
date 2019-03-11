
define(['weui'], function(weui){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', '$stateParams', 'Md5', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state,$stateParams,Md5){

        $scope.authInfo = JSON.parse($stateParams.authInfo);
		
        $scope.resetPwd = function(newPwd){

			if (newPwd == undefined || newPwd == null || newPwd == ''){
                weui.topTips('请输入新密码', 3000);
				return false;
            }
			
			var loading = weui.loading('提交中...');
			
			newPwd = Md5.b64_hmac_md5("hk",newPwd);//使用md5对密码加密,并转换为HEX
			
            $http({
                method: 'post',
                url: URL+'doctor/resetPwd',
                data: {
					phone: $scope.authInfo.phone,
					code: $scope.authInfo.code,
					newPwd: newPwd
                }
            })
            .success(function(resp){
				
				loading.hide();

                if (resp.code == 1){

                    weui.toast("重置成功",1000);
					
					//$location.path('login').replace();
					$window.history.go(-3);//返回到上，上，上一页

                    return false;

                } else{

					weui.alert(resp.msg);

					console.log(resp)
				}

            })
			.error(function(data){
				
				//$rootScope.myloader = false;
				loading.hide();
				
				weui.alert('系统服务异常，请联系管理员');
				
			})
        }
	
    }];

});
