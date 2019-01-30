
define([], function(){

    return ['$scope', '$http','$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state,AppCtrl){

		$scope.doctorObj = dataVer.get('doctorInfo');

		$scope.ok = function(){
			var signature = $scope.accept();
			if(signature.isEmpty){
				alert("请手写签名");
			}else{
				$scope.doctorObj.signatureurl = signature.dataUrl;
				dataVer.put('doctorInfo', $scope.doctorObj);//在每次进入修改页之前，应该使用doctor数据初始化signatureurl
				$state.go('updateInfo');
			}
		}
		
		$scope.goBack = function() {
			$window.history.back();
		}
    }];

});
