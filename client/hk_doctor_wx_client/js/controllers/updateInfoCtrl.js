
define([], function(){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

        //默认值
		$scope.doctorObj = dataVer.get('doctorInfo');

        $scope.update = function(){

            if ($scope.doctorObj.name == null || $scope.doctorObj.name == '' || $scope.doctorObj.name == undefined){
                alert('请输入医生姓名')
				return false;
            }
			if ($scope.doctorObj.department == null || $scope.doctorObj.department == '' || $scope.doctorObj.department == undefined){
                alert('请输入科室')
				return false;
            }
			if ($scope.doctorObj.hospitalid == null || $scope.doctorObj.hospitalid == undefined){
                alert('请选择所在医院')
				return false;
            }
			if ($scope.signatureurl == null || $scope.signatureurl == '' || $scope.signatureurl == undefined){
                alert('请设置手写签名')
				return false;
            }
			
			
			$rootScope.myloader = true;
			
            $http({
                method: 'post',
                url: URL+'doctor/updateInfo',
                data: {
					hid: $scope.doctorObj.hospitalid,
					name: $scope.doctorObj.name,
                    department: $scope.doctorObj.depart,
					signatureurl: $scope.doctorObj.signatureurl
                }
            })
            .success(function(resp){
				
				$rootScope.myloader = false;

                if (resp.code == 1){

                    dataVer.put('doctorInfo',resp.data);
					
					$state.go('index');

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
		
		$scope.goBack = function() {
			$window.history.back();
		}
		
		$scope dataURItoFile = function(dataURI, fileName) { 
			var imgType = atob(dataURI.split(',')[0]);
			var byteString = atob(dataURI.split(',')[1]); 
			var ab = new ArrayBuffer(byteString.length); 
			var ia = new Uint8Array(ab); 
			for (var i = 0; i < byteString.length; i++) { 
				ia[i] = byteString.charCodeAt(i); 
			} 
			// return new Blob([ab], { type: 'image/jpeg' }); 
			return new File([ia], fileName, {type: imgType, lastModified: Date.now()}) 
		} 
    }];

});
