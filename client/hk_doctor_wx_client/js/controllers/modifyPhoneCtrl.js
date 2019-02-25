
define(['weui'], function(weui){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

        $scope.modifyPhone = function(pwd, phone){

            if (pwd == undefined || pwd == null || pwd == ''){
                weui.topTips('请输入登陆密码', 3000);
				return false;
            }
			if (phone == undefined || phone == null || phone == ''){
                weui.topTips('请输入手机号', 3000);
				return false;
            }
			if (phone.length != 11){
                weui.topTips('请输入正确的手机号码', 3000);
				return false;
            }
			
			var loading = weui.loading('提交中...');
			
            $http({
                method: 'post',
                url: URL+'doctor/modifyPhone',
                data: {
					pwd: pwd,
					phone: phone
                }
            })
            .success(function(resp){
				
				loading.hide();

                if (resp.code == 1){

                    weui.toast("修改成功",1000);
					
					$window.history.back();

                    return false

                } else if(resp.code == 4){
					
					weui.alert(resp.msg);
					
					$state.go('login');
				}else{

					weui.alert(resp.msg);

					console.log(data)
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
