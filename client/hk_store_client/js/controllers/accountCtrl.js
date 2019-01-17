
define(['jquery'], function($){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值

		$scope.searchopt= { pageindex : 1};
		
		$scope.balance = 0;
				
		$scope.recordList = [];

        $scope.getBalance = function (){
			
			//$rootScope.myloader = true;

			$http({
                method: 'get',
                url: URL3+'account/getStoreBalance',
                requestType: 'json',
                params: {
                }
            })
            .success(function(resp){

				//$rootScope.myloader = false;

                if (resp.code == 1){

                    $scope.balance = resp.data;
					
				}else if(resp.code == 4){
					alert(resp.msg);
					
					$state.go('login');
                }else{
				
					alert(resp.msg);
				
				}

            })
			.error(function(data){
				
				//$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})
			
        };
		
		$scope.getRecordsByFirstPage = function(){
			
			$scope.searchopt.pageindex = 1;

			$scope.getRecordsList();

		};
		
		$scope.getRecordsByNextPage = function(){
			$scope.searchopt.pageindex = $scope.searchopt.pageindex+1;
			
			$scope.getRecordsList();
		};
		
		$scope.getRecordsByPrePage = function(){
			$scope.searchopt.pageindex = $scope.searchopt.pageindex-1;
			
			$scope.getRecordsList();
		};
		
		
        $scope.getRecordsList = function(){

		   	$rootScope.myloader = true;
						
            $http({
                method: 'get',
                url: URL3+'account/getStoreAccountRecord',
                requestType: 'json',
                params: {
					startDate: $scope.searchopt.startdate || '',
					endDate: $scope.searchopt.enddate || '',
                    pageIndex : $scope.searchopt.pageindex,
					pageSize : 20
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
						
                    $scope.recordList = resp.data;
					
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
		
		
		$scope.format = function(time, format){
			var t = new Date(time);
			var tf = function(i){return (i < 10 ? '0' : '') + i};
			return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
				switch(a){
					case 'yyyy':
						return tf(t.getFullYear());
						break;
					case 'MM':
						return tf(t.getMonth() + 1);
						break;
					case 'mm':
						return tf(t.getMinutes());
						break;
					case 'dd':
						return tf(t.getDate());
						break;
					case 'HH':
						return tf(t.getHours());
						break;
					case 'ss':
						return tf(t.getSeconds());
						break;
				}
			})
		};
		
		$scope.f2y = function( num ) {
			if ( typeof num !== "number" || isNaN( num ) ) return null;
			return ( num / 100 ).toFixed( 2 );
		}
		
		$scope.enterEvent = function(e) {
			var keycode = window.event?e.keyCode:e.which;
			if(keycode==13){//回车
				$scope.getRecordsByFirstPage();
			}
		}
		
		$scope.$watch('$viewContentLoaded', function() {  
			// 页面加载完执行
			$scope.getBalance();
		}); 
		

    }];

});
