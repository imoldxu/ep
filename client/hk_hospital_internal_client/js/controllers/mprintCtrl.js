
define(['angular','layer'], function(angular,layer){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window,$location,$rootScope,dataVer,$state){

        //默认值

        $scope.userArry = dataVer.get('userInfo');

        $scope.drugArry = dataVer.get('drugInfo');

        var date = new Date();

        $scope.dateTime = date.getFullYear() +"/"+(date.getMonth()+1) + '/' + date.getDate();

        $scope.price = 0;

        angular.forEach($scope.drugArry, function(data,index,array){


            $scope.price  = $scope.price  + parseFloat(data.price)*data.number;


        });

        $scope.goBack = function(){

            $window.history.back()

        };



        $scope.print = function(){

            //$window.print();

            if(document.execCommand("print") == true){

                layer.confirm('是否完成打印？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    setTimeout(function(){

                        layer.closeAll();

                        $window.history.back();

                    },500)

                });

            }

        }
		
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
    }];

});
