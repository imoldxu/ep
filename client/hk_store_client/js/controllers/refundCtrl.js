
define(['jquery'], function($){

    return ['$scope', '$http', '$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state){

        $scope.prescription = dataVer.get('prescriptionInfo');

        $scope.drugList = dataVer.get('drugList');

		$scope.refund = function (){

			var refundDrugList = [];
		
			for(var i=0 ;i<$scope.drugList.length; i++){
				
				var x = $scope.drugList[i];
				if(x.number>0){//只有大于0的才可以退
					if(x.number>=x.refundnum){
						if(x.refundnum>0){//只有要买的数量大于1的才提交
					
							var transInfo = {};
						
							transInfo.drugid = x.drugid;
						
							transInfo.num = x.refundnum;
						
							refundDrugList.push(transInfo);
						}
					}else{
						alert("退货数量不可超过销售数量");
						
						return false;
					}
				}
				
			}
			if(refundDrugList.length == 0){
				alert("退货清单不能为空");
				return false;
			}
			
			$rootScope.myloader = true;
		
			$http({
                method: 'post',
                url: URL3+'prescription/refund',
                data: {
                    pid : $scope.prescription.id,
					refundDrugsStr: JSON.stringify(refundDrugList) 
                }
            })
            .success(function(resp){

				$rootScope.myloader = false;

                if (resp.code == 1){

					alert("退货成功");
					
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
