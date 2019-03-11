
define(['weui'], function(weui){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

		weui.alert($window.history.length);
        //默认值
		$scope.doctorObj = dataVer.get('doctorInfo');

        $scope.update = function(){

            if ($scope.doctorObj.name == undefined || $scope.doctorObj.name == null || $scope.doctorObj.name == ''){
                weui.topTips('请输入医生姓名', 3000);
				return false;
            }
			if ($scope.doctorObj.department == undefined || $scope.doctorObj.department == null || $scope.doctorObj.department == ''){
                weui.topTips('请输入科室', 3000);
				return false;
            }
			if ($scope.doctorObj.hospitalid == undefined || $scope.doctorObj.hospitalid == null ){
                weui.topTips('请选择所在医院', 3000);
				return false;
            }
			if ($scope.doctorObj.signatureurl == undefined || $scope.doctorObj.signatureurl == null || $scope.doctorObj.signatureurl == ''){
                weui.topTips('请设置手写签名', 3000);
				return false;
            }
			
			
			$rootScope.myloader = true;
			
            $http({
                method: 'post',
                url: URL+'doctor/updateInfo',
                data: {
					hid: $scope.doctorObj.hospitalid,
					name: $scope.doctorObj.name,
                    department: $scope.doctorObj.department,
					signatureurl: $scope.doctorObj.signatureurl
                }
            })
            .success(function(resp){
				
				$rootScope.myloader = false;

                if (resp.code == 1){

					weui.toast('修改成功', 3000);

                    dataVer.put('doctorInfo',resp.data);
					
					if($window.history.length>2){//chrome本身就是1，把当前页面压进history了就是2
						$window.history.back();
					}else{
						$state.go('home');
					}

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
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})
        }
		
		$scope.initHospital = function(){
			$http({
                method: 'get',
                url: URL+'hospital/getHospitalList',
                params: {
				}
            })
            .success(function(resp){

                if (resp.code == 1){

                    $scope.hospitalList = resp.data;
					
                    return false

                } else if(resp.code == 4){
					
					alert(resp.msg);
					
					$state.go('login');
				}else{

					alert(resp.msg);

					console.log(data)
				}

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})
		}
		
		$scope.openSignaturePad = function(){
			dataVer.put('doctorInfo', $scope.doctorObj);//保存修改的内容，以便回到该页面依旧可用
			$state.go('signaturePad');
		}
		
		$scope.$watch('$viewContentLoaded', function() {  
			// 页面加载完执行
			$scope.initHospital();
		});
		
    }];

});
