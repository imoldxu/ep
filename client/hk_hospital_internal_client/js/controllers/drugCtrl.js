
define(['jquery'], function($){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值

        $scope.ipt={};

        $scope.modal = false;

        $scope.modalArry = {};

        $scope.optNum = '';

        $scope.closeMod = function(){

            $scope.modal = false

        };

        $scope.goLogin = function(){

            $state.go('welcome')

        };

        $scope.openModal = function (id,i){

            $scope.modal = true;

            $scope.optNum = i;

            $scope.modalArry = $scope.optArry[i];

            console.log($scope.modalArry)

        };
		
		$scope.deldrug = function (id,i){

            $scope.optNum = i;

            $scope.modalArry = $scope.optArry[i];

			$rootScope.myloader = true;
			
            $http({
                method: 'post',
                url: URL+'drug/delDrug',

                params: {
                    drugid: $scope.modalArry.id
                }
            })
            .success(function(data){

				
				$rootScope.myloader = false;
			
                $scope.modal = false;

                if (data.code == 1){

					$scope.optArry.splice(i,1);
				
                    alert('删除成功');

                    return false;
                }

                alert('删除失败');

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})

        };

		$scope.updrug = function (id,i){

            $scope.optNum = i;

            $scope.modalArry = $scope.optArry[i];

			$rootScope.myloader = true;
			
            $http({
                method: 'post',
                url: URL+'drug/upDrug',

                params: {
                    drugid: $scope.modalArry.id
                }
            })
            .success(function(data){

				
				$rootScope.myloader = false;
			
                $scope.modal = false;

                if (data.code == 1){
					
					$scope.optArry[$scope.optNum].state = 1;
				
                    //alert('修改成功');

                    return false;
                }

                alert('修改失败');

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})

        };
		
		$scope.downdrug = function (id,i){

            $scope.optNum = i;

            $scope.modalArry = $scope.optArry[i];

			$rootScope.myloader = true;
			
            $http({
                method: 'post',
                url: URL+'drug/downDrug',

                params: {
                    drugid: $scope.modalArry.id
                }
            })
            .success(function(data){

				
				$rootScope.myloader = false;
			
                $scope.modal = false;

                if (data.code == 1){
					
					$scope.optArry[$scope.optNum].state = 0;
				
                    //alert('修改成功');

                    return false;
                }

                alert('修改失败');

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})

        };
		
        $scope.addDrugInfo = function(){

            $scope.optArry[$scope.optNum] = $scope.modalArry;

			$rootScope.myloader = true;
			
            $http({
                method: 'post',
                url: URL+'drug/modifyDrug',

                params: {
                    drugInfo: JSON.stringify($scope.modalArry)
                }
            })
            .success(function(data){

                $scope.modal = false;

				$rootScope.myloader = false;
				
                if (data.code == 1){

                    alert('修改成功');

                    return false;
                }else{

					alert('修改失败');
				}
            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})


        }


        $scope.getDrugs = function(name){

            if (name == undefined || name == '' || name.length <= 1 ){

				name = '';
            }

			
			$rootScope.myloader = true;
			
            $http({
                method: 'get',
                url: URL+'drug/getDrugInfoListByKeys',
                requestType: 'json',
                params: {
                    keys: name
                }
            })
            .success(function(data){
			
				$rootScope.myloader = false;

                if (data.code == 1){

                    $scope.gMod = true;

                    $scope.optArry = data.data;
                }else{
					alert(data.msg);
				}

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})

        };


        $('#file').change(function(){


            var file = $('#file')[0].files[0];

            var fd = new FormData();

            fd.append('file', file);

			
			$rootScope.myloader = true;
			
            $http({
                method:'POST',
                url:URL+'/drug/uploadByExcel',
                data: fd,
                headers: { 'Content-Type': undefined }


            })
			.success( function ( data )
			{

				$rootScope.myloader = false;
			
				if (data.code == 1){

					alert("上传成功");

					$scope.optArry = data.data;

					return false

				}

				alert("上传失败");


			})
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})


        })

    }];

});
