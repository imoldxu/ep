
define(['weui'], function(weui){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', '$stateParams', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state,$stateParams){

        $scope.phone = null;
		
        $scope.getCode = function(){

			if ($scope.phone == undefined || $scope.phone == null || $scope.phone == ''){
                weui.topTips('请输入手机号', 3000);
				return false;
            }
			
			var loading = weui.loading('提交中...');
			
			$http({
                method: 'get',
                url: URL+'doctor/getVerifyCode',
                params: {
					phone: $scope.phone
				}
            })
            .success(function(resp){
				
				loading.hide();

                if (resp.code == 1){

					weui.toast('验证码已发送',1000);

					var verifyInfo = {};
					verifyInfo.phone = $scope.phone;
					verifyInfo.nextPage = 'resetPwd';

					$state.go('verifyCode', {verifyInfo: JSON.stringify(verifyInfo)});

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
