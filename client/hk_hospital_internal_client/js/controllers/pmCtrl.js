
define(['jquery'], function($){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值

		$scope.searchopt= dataVer.get('serachOpt') || { pageindex : 0};
				
		$scope.optArry = dataVer.get('pList') || [];

        $scope.print = function (id,i){
			
			$rootScope.myloader = true;

			$http({
                method: 'get',
                url: URL+'prescition/getPrescriptionByID',
                requestType: 'json',
                params: {
                    id : $scope.optArry[i].id
                }
            })
            .success(function(data){

				$rootScope.myloader = false;

                if (data.code == 1){

                    data.data;
					
					dataVer.put('userInfo',	data.data.prescription);

					dataVer.put('drugInfo', data.data.drugList);
					
					console.log(data.data.drugList);
			
					if(data.data.prescription.type==1){
						$state.go('mprint');
					}else{
						$state.go('mzyprint');
					}
                }else{
				
					alert(data.msg);
				
				}

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})
			
        };

		$scope.complete = function (id,i){

			$rootScope.myloader = true;
		
			$http({
                method: 'post',
                url: URL+'prescition/over',
                requestType: 'json',
                params: {
                    id : $scope.optArry[i].id
                }
            })
            .success(function(data){

			
				$rootScope.myloader = false;

                if (data.code == 1){

                    $scope.optArry[i] = data.data;
					
					dataVer.put('pList', $scope.optArry);
					
					$scope.print(id,i);
					
                }else{
				
					alert(data.msg);
				
				}

            })
			.error(function(data){
				
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})
			
        };
		
		$scope.cancel = function (id,i){
			
			$rootScope.myloader = true;

			$http({
                method: 'post',
                url: URL+'prescition/rollback',
                requestType: 'json',
                params: {
                    id : $scope.optArry[i].id
                }
            })
            .success(function(data){

				$rootScope.myloader = false;
			
                if (data.code == 1){

                    $scope.optArry[i] = data.data;	
					
					dataVer.put('pList', $scope.optArry);
					
                }else{
				
					alert(data.msg);
				
				}

            })
			.error(function(data){
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			})
			
        };
		
        $scope.downloadExcel = function(){
		
            $window.location = URL+'prescition/downloadExcel';
		
		};

		
		$scope.getPrescriptionByFirstPage = function(){
			
			$scope.searchopt.pageindex = 0;

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
                url: URL+'prescition/getPrescriptionList',
                requestType: 'json',
                params: {
                    option : JSON.stringify($scope.searchopt)
                }
            })
            .success(function(data){

				$rootScope.myloader = false;

                if (data.code == 1){

					if(data.data.length == 0){
						if($scope.searchopt.pageindex == 0){
							alert("暂无数据");
							return false;
						}else{
							$scope.searchopt.pageindex = $scope.searchopt.pageindex - 1;
							dataVer.put('serachOpt', $scope.searchopt);
							alert("已经是最后一页");
							return false;
						}
					}
						
                    $scope.optArry = data.data;
					
					dataVer.put('pList', data.data);
					
					console.log(data.data);
					
                }else{
					
					alert(data.msg);
				
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
