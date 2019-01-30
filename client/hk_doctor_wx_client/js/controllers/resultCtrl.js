
define(['angular','layer'], function(angular,layer){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window,$location,$rootScope,dataVer,$state){

        //默认值
		$scope.prescriptionObj = JSON.parse(dataVer.get('resultInfo'));
		
        $scope.storeList = $scope.prescriptionObj.storeList;

		$scope.noneStoreDrugList = null;
		
		if($scope.storeList.length > 0){
			if($scope.storeList[0].name == null){
				$scope.noneStoreDrugList = $scope.storeList[0].drugList;
				$scope.storeList.splice(0,1);
			}
		}
		
        //$scope.drugList = JSON.parse($scope.prescriptionObj.drugListStr);

        var date = new Date();

        $scope.dateTime = date.getFullYear() +"/"+(date.getMonth()+1) + '/' + date.getDate();

        $scope.goBack = function(){

            $window.history.back();

        };

		$scope.f2y = function( num ) {
			if ( typeof num !== "number" || isNaN( num ) ) return null;
			return ( num / 100 ).toFixed( 2 );
		}

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
