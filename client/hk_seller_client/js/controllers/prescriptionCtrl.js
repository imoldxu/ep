
define(['jquery'], function($){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state){

        //默认值
		$scope.pid = null;
		
		//$scope.storeInfo = dataVer.get('storeInfo');
		
		$scope.prescription = {};//处方信息
				
		$scope.drugList = [];//药品清单

		//$scope.buyDrugList = [];//药品清单
		
        $scope.getPrescriptionByID = function (){
			
			$rootScope.myloader = true;

			$http({
                method: 'get',
                url: URL3+'prescription/getPrescriptionByIDFromStore',
                requestType: 'json',
				params: {
                    pid : $scope.pid
                }
            })
            .success(function(resp){

				$rootScope.myloader = false;

                if (resp.code == 1){

                    $scope.prescription = resp.data;
					
					$scope.drugList = resp.data.drugList;
			
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

		$scope.buyFromStore = function (){

			var buyDrugList = [];
		
			for(var i=0 ;i<$scope.drugList.length; i++){
				
				var x = $scope.drugList[i];
				if((x.number-x.soldnumber)>0){//只处理还有可以买的项
					if((x.number-x.soldnumber)>=x.buynum){
						if(x.buynum>0){//只有要买的数量大于1的才提交
					
							var transInfo = {};
						
							transInfo.drugid = x.drugid;
						
							transInfo.num = x.buynum;
						
							buyDrugList.push(transInfo);
						}
					}else{
						alert("购买数量不可超过处方要求");
						
						return false;
					}
				}
				
			}
			
			$rootScope.myloader = true;
		
			$http({
                method: 'post',
                url: URL3+'prescription/buyFromStore',
                //requestType: 'json',
                data: {
                    pid : $scope.prescription.id,
					drugList: JSON.stringify(buyDrugList) 
                }
            })
            .success(function(data){

				$rootScope.myloader = false;

                if (data.code == 1){

					alert("购买成功");
					
					dataVer.put('prescriptionInfo',	resp.data);

					dataVer.put('drugList', resp.data.drugList);
					
					if(resp.data.type==1){
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
				$scope.getPrescriptionByID();
			}
		};

    }];

});
