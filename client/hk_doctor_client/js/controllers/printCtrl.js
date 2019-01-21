
define(['angular','layer'], function(angular,layer){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window,$location,$rootScope,dataVer,$state){

        //默认值
		$scope.doctorObj = dataVer.get('doctorInfo');
		
        $scope.prescriptionObj = dataVer.get('prescriptionInfo');

        $scope.drugList = dataVer.get('drugList');

        var date = new Date();

        $scope.dateTime = date.getFullYear() +"/"+(date.getMonth()+1) + '/' + date.getDate();

        $scope.goBack = function(){

            $window.history.back()

        };

        $scope.commit = function(){
		
			$rootScope.myloader = true;
			
			$http({
				method: 'post',

				url: URL+'prescition/open',

				requestType: 'json',

				params: {

					doctorid: $scope.doctorObj.id,
					
					hospitalid: $scope.doctorObj.hospitalid,
				
					prescriptionInfo: JSON.stringify($scope.prescriptionObj),

					drugList: JSON.stringify($scope.drugList)
				}
			})
			.success(function(data){
				
				$rootScope.myloader = false;

				if (data.code == 1){
			
					
					var docArray = {

						doctorname : $scope.prescriptionObj.doctorname,

						department : $scope.prescriptionObj.department

					}

					dataVer.put('docArray',docArray);

					alert("提交成功");
					
					$state.go('welcome');
				}else{
				
					alert(data.msg);

					console.log(data)
				}
			
			})
			.error(function(data){
			
				$rootScope.myloader = false;
			
				alert('系统服务异常，请联系管理员');
			
			});
           
        }
    }];

});
