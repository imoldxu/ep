
define(['weui'], function(weui){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

        //默认值
		$scope.doctorObj = dataVer.get('doctorInfo');

        $scope.modifyPwd = function(oldPwd, newPwd){

            if (oldPwd == undefined || oldPwd == null || oldPwd == ''){
                weui.topTips('请输入旧密码', 3000);
				return false;
            }
			if (newPwd == undefined || newPwd == null || newPwd == ''){
                weui.topTips('请输入新密码', 3000);
				return false;
            }
			if (oldPwd == newPwd){
                weui.topTips('新旧密码不能一致', 3000);
				return false;
            }
			
			//$rootScope.myloader = true;
			var loading = weui.loading('提交中...');
			
            $http({
                method: 'post',
                url: URL+'user/modifyPwd',
                data: {
					oldPwd: oldPwd,
					newPwd: newPwd
                }
            })
            .success(function(resp){
				
				//$rootScope.myloader = false;
				loading.hide();

                if (resp.code == 1){

                    weui.toast("修改成功",1000);
					
					$state.goBack();

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
