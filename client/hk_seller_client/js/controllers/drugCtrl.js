
define(['jquery'], function($){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值

        $scope.searchopt= {pageindex:1};

		$scope.drugList = [];
		
        $scope.drugInfo = {};

		$scope.getDrugsByFirstPage = function(){
			
			$scope.searchopt.pageindex = 1;

			$scope.getDrugs();
		
		};
		
		$scope.getDrugsByNextPage = function(){
			$scope.searchopt.pageindex = $scope.searchopt.pageindex+1;
			
			$scope.getDrugs();
		};
		
		$scope.getDrugsByPrePage = function(){
			$scope.searchopt.pageindex = $scope.searchopt.pageindex-1;
			
			$scope.getDrugs();
		};

        $scope.getDrugs = function(){

			$rootScope.myloader = true;
			
            $http({
                method: 'get',
                url: URL2+'hospital/getDrugListBySeller',
                requestType: 'json',
                params: {
					key : $scope.searchopt.key || '',
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
							return false;
						}else{
							$scope.searchopt.pageindex = $scope.searchopt.pageindex - 1;
							alert("已经是最后一页");
							return false;
						}
					}
				
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
