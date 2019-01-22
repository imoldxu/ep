
define(['angular','layer'], function(angular,layer){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window,$location,$rootScope,dataVer,$state){

        //默认值
		$scope.prescriptionObj = JSON.parse(dataVer.get('resultInfo'));
		
        $scope.storeList = $scope.prescriptionObj.storeList;

        $scope.drugList = $scope.prescriptionObj.drugList;

        var date = new Date();

        $scope.dateTime = date.getFullYear() +"/"+(date.getMonth()+1) + '/' + date.getDate();

        $scope.goBack = function(){

            $window.history.back();

        };

        $scope.print = function(){
		
			if(document.execCommand("print") == true){

				layer.confirm('是否完成打印？', {
					btn: ['确定','取消'] //按钮
				}, function(){
					setTimeout(function(){

						layer.closeAll();

						$state.go('index');

					},500);

				});

			}else{
				alert("打印服务异常，请联系管理员");
			}      
        }
    }];

});
