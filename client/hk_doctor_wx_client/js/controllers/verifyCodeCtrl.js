
define(['weui'], function(weui){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', '$stateParams','$interval', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state,$stateParams,$interval){

        $scope.verfiyInfo = JSON.parse($stateParams.verifyInfo);
		$scope.vcodeEnable = false;
		$scope.countDownSecond = 60;
		$scope.vcodelabel = $scope.countDownSecond+'s后重发';
			
		$scope.countDown = $interval(function(){
			$scope.countDownSecond = $scope.countDownSecond - 1;  
			$scope.vcodelabel = $scope.countDownSecond+'s后重发';
			if ($scope.countDownSecond == 0) {  

				$interval.cancel($scope.countDown)
				$scope.vcodelabel = '重新发送';  
				$scope.vcodeEnable = true;

				return false;  
			}  

		}, 1000);	
			
        $scope.verifyCode = function(code){

			if (code == undefined || code == null || code == ''){
                weui.topTips('请输入验证码', 3000);
				return false;
            }
			
			var loading = weui.loading('提交中...');
			
			
            $http({
                method: 'post',
                url: URL+'doctor/verifyCode',
                data: {
					phone: $scope.verfiyInfo.phone,
					code: code
				}
            })
            .success(function(resp){
				
				loading.hide();

                if (resp.code == 1){

					var authInfo = {};
					
					authInfo.code = resp.data;
					
					authInfo.phone = $scope.verfiyInfo.phone;
					
					$state.go($scope.verfiyInfo.nextPage, {authInfo: JSON.stringify(authInfo)});

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
		
		
		
		$scope.getCode = function(){

			$scope.vcodeEnable = false;
			$scope.countDownSecond = 60;
			$scope.vcodelabel = $scope.countDownSecond+'s后重发';
			
			$scope.countDown = $interval(function(){
				$scope.countDownSecond = $scope.countDownSecond - 1;  
				$scope.vcodelabel = $scope.countDownSecond+'s后重发';
				if ($scope.countDownSecond == 0) {  
					
					$interval.cancel($scope.countDown)
					$scope.vcodelabel = '重新发送';  
					$scope.vcodeEnable = true;
					
					return false;  
				}  
				
			}, 1000);	
			
			$http({
				method: 'get',
				url: URL+'doctor/getVerifyCode',
				params: {
					phone: $scope.verfiyInfo.phone
				}
			})
			.success(function(resp){
				
				loading.hide();

				if (resp.code == 1){

					weui.toast('验证码已发送',1000);

					return false;

				} else{

					weui.alert(resp.msg);

					console.log(data)
				}

			})
			.error(function(data){
				
				loading.hide();
				
				weui.alert('系统服务异常，请联系管理员');
				
			})
        }
		
		$scope.$watch('$viewContentLoaded', function() {

			//自动获取授权码
			//$scope.getCode();
			
		});
	
    }];

});
