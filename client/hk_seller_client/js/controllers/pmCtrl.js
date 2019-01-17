
define(['jquery'], function($){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值

		$scope.searchopt= dataVer.get('serachOpt') || { pageindex : 1};
				
		$scope.prescriptionList = dataVer.get('pList') || [];

        $scope.print = function (id,i){
			
			$rootScope.myloader = true;

			$http({
                method: 'get',
                url: URL3+'prescription/getStorePrescriptionDetail',
                requestType: 'json',
                params: {
                    pid : $scope.prescriptionList[i].id
                }
            })
            .success(function(resp){

				$rootScope.myloader = false;

                if (resp.code == 1){

                    resp.data;
					
					dataVer.put('prescriptionInfo',	resp.data);

					dataVer.put('drugList', resp.data.drugList);
					
					console.log(resp.data.drugList);
			
					if(resp.data.type==1){
						$state.go('mprint');
					}else{
						$state.go('mzyprint');
					}
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
		
		$scope.getPrescriptionByFirstPage = function(){
			
			$scope.searchopt.pageindex = 1;

			$scope.getPrescriptionList();
			
			$scope.searchopt.number = '';
			dataVer.put('serachOpt', $scope.searchopt);
		};
		
		$scope.getPrescriptionByNextPage = function(){
			$scope.searchopt.pageindex = $scope.searchopt.pageindex+1;
			
			dataVer.put('serachOpt', $scope.searchopt);
			
			$scope.getPrescriptionList();
		};
		
		$scope.getPrescriptionByPrePage = function(){
			$scope.searchopt.pageindex = $scope.searchopt.pageindex-1;
			
			dataVer.put('serachOpt', $scope.searchopt);
			
			$scope.getPrescriptionList();
		};
		
		
        $scope.getPrescriptionList = function(){

		   	$rootScope.myloader = true;
						
            $http({
                method: 'get',
                url: URL3+'prescription/getStorePrescriptions',
                requestType: 'json',
                params: {
				    startDate : $scope.searchopt.startdate || '',
					endDate : $scope.searchopt.enddate || '',
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
							dataVer.put('serachOpt', $scope.searchopt);
							alert("已经是最后一页");
							return false;
						}
					}
						
                    $scope.prescriptionList = resp.data;
					
					dataVer.put('pList', resp.data);
					
					console.log(resp.data);
					
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
		
		$scope.enterEvent = function(e) {
			var keycode = window.event?e.keyCode:e.which;
			if(keycode==13){//回车
				$scope.getPrescriptionByFirstPage();
			}
		};

    }];

});
