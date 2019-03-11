
define(['jquery'], function($){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值

        $scope.searchopt= {pageindex:1};

		$scope.drugList = [];
		
        $scope.modal = false;

        $scope.drugInfo = {};

        $scope.optNum = '';

        $scope.closeMod = function(){

            $scope.modal = false

        };

        $scope.openModal = function (id,i){

            $scope.modal = true;

            $scope.optNum = i;

            $scope.drugInfo = $scope.drugList[i];

            console.log($scope.drugInfo)

        };

		$scope.updrug = function (id,i){

            $scope.optNum = i;

            $scope.drugInfo = $scope.drugList[i];

			$rootScope.myloader = true;
			
            $http({
                method: 'post',
                url: URL2+'store/upDrug',
                data: {
                    storedrugId: $scope.drugInfo.id
                }
            })
            .success(function(resp){

				
				$rootScope.myloader = false;
			
                $scope.modal = false;

                if (resp.code == 1){
					
					$scope.drugList[$scope.optNum].state = 1;
				
                    //alert('修改成功');

                    return false;
				} else if( resp.code == 4){
					alert(resp.msg);
					$state.go('login');
				}else{
					alert(resp.msg);
				}
            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})

        };
		
		$scope.downdrug = function (id,i){

            $scope.optNum = i;

            $scope.drugInfo = $scope.drugList[i];

			$rootScope.myloader = true;
			
            $http({
                method: 'post',
                url: URL2+'store/downDrug',
                data: {
                    storedrugId: $scope.drugInfo.id
                }
            })
            .success(function(resp){

				
				$rootScope.myloader = false;
			
                $scope.modal = false;

                if (resp.code == 1){
					
					$scope.drugList[$scope.optNum].state = 2;
				
                    console('修改成功');

                    return false;
                }else if(resp.code == 4){
					alert(resp.msg);
					
					$state.go('login');
				}else{
					alert(resp.msg);
				}
            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})

        };
		
        $scope.modifyDrugInfo = function(){

			$rootScope.myloader = true;
			
            $http({
                method: 'post',
                url: URL2+'store/modifyPrice',
                data: {
                    storedrugId: $scope.drugInfo.id,
					price: parseFloat($scope.drugInfo.showprice)*100
                }
            })
            .success(function(resp){

				$rootScope.myloader = false;
				
                if (resp.code == 1){

					$scope.modal = false;
					
                    alert('修改成功');
					
					$scope.drugInfo.price = parseFloat($scope.drugInfo.showprice)*100;
					
					$scope.drugList[$scope.optNum] = $scope.drugInfo;

                }else if(resp.code == 4){
					$scope.modal = false;
					alert(resp.msg);
					$state.go('login');
				}else{
					alert(resp.msg);
				}
            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})


        }

		$scope.getDrugsByFirstPage = function(){
			
			$scope.searchopt.pageindex = 1;

			$scope.getDrugs();
			
			//dataVer.put('serachOpt', $scope.searchopt);
		};
		
		$scope.getDrugsByNextPage = function(){
			$scope.searchopt.pageindex = $scope.searchopt.pageindex+1;
			
			//dataVer.put('serachOpt', $scope.searchopt);
			
			$scope.getDrugs();
		};
		
		$scope.getDrugsByPrePage = function(){
			$scope.searchopt.pageindex = $scope.searchopt.pageindex-1;
			
			//dataVer.put('serachOpt', $scope.searchopt);
			
			$scope.getDrugs();
		};

        $scope.getDrugs = function(){

            //if (name == undefined || name == '' || name.length <= 1 ){

			//	name = '';
            //}

			
			$rootScope.myloader = true;
			
            $http({
                method: 'get',
                url: URL2+'store/getStoreDrugList',
                requestType: 'json',
                params: {
					key : $scope.searchopt.key || '',
					state : $scope.searchopt.state || 0,
					pageIndex: $scope.searchopt.pageindex,
					pageSize: 20
                }
            })
            .success(function(resp){
			
				$rootScope.myloader = false;

                if (resp.code == 1){

					if(resp.data.length == 0){
						if($scope.searchopt.pageindex == 1){
							alert("暂无数据");
						}else{
							$scope.searchopt.pageindex = $scope.searchopt.pageindex - 1;
							alert("已经是最后一页");
							return false;
						}
					}
				
                    //$scope.gMod = true;

                    $scope.drugList = resp.data;
					
                }else if(resp.code == 4){
					alert(resp.msg);
					$state.go('login');
				}else{
					alert(resp.msg);
				}

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})

        };

		$scope.f2y = function( num ) {
			if ( typeof num !== "number" || isNaN( num ) ) return null;
			return ( num / 100 ).toFixed( 2 );
		}
		

    }];

});
