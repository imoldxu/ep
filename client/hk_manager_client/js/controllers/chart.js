'use strict';

/* Controllers */

app
  // Flot Chart controller 
  .controller('FlotChartDemoCtrl', ['$scope','$http', '$state', '$log', function($scope, $http, $state, $log) {
    
	$scope.dayIncome = {data: [], ticks: []};
	$scope.monthIncome = {data: [], ticks: []};
    $scope.hospitalPrescriptionNum = {data: [], timeType: 1 };
	$scope.doctorPrescriptionNum = {data: [], timeType: 1 };
	$scope.storeSales =  {data: [], timeType: 1 };
	$scope.drugSales = {data: [], timeType: 1 };
	
	
	$scope.refreshDayIncome = function(){
		var size = 7;//7天的数据
		
		var date = new Date();
		//date.setMonth(date.getMonth()-1);
		
		var datetime = date.getTime();
		for(var i=size; i>0; i--){
			var temp = [];
			temp.push(i);
			var day = (date.getMonth() + 1) + '-' + date.getDate();
			temp.push(day);
			
			$scope.dayIncome.ticks.push(temp);
			
			date.setDate(date.getDate()-1);
		}
		
		$http({
			method: "GET",
			url: $scope.app.url_prescription+"record/getDaysIncome",
			params:{
				time: datetime,
				size: size
			}
		})
		.success(function(response){
			if(response.code == 1){
				$scope.dayIncome.data = response.data;
			}else if(response.code == 4){
				alert(response.msg);
				$state.go('access.signin');
			}else{
				alert(response.msg);
			}
		})
		.error(function(data){
			alert('网络异常，请稍后再试');
		});
	}
	
	$scope.refreshMonthIncome = function(){
		var size = 12;//12个月的数据
		
		var date = new Date();
		var datetime = date.getTime();
		for(var i=size; i>0; i--){
			var temp = [];
			temp.push(i);
			var month = date.getMonth()+1;
			temp.push(month);
			
			$scope.monthIncome.ticks.push(temp);
			
			date.setMonth(date.getMonth()-1);
		}
		
		$http({
			method: "GET",
			url: $scope.app.url_prescription+"record/getMonthsIncome",
			params:{
				time: datetime,
				size: size
			}
		})
		.success(function(response){
			if(response.code == 1){
				$scope.monthIncome.data = response.data;
			}else if(response.code == 4){
				alert(response.msg);
				$state.go('access.signin');
			}else{
				alert(response.msg);
			}
		})
		.error(function(data){
			alert('网络异常，请稍后再试');
		});
		
	}
	
	$scope.refreshHospitalPrescriptionNum = function(){
		var size = 6;//12个月的数据
		
		var startDate;
		var endDate;
		var now = new Date();
		
		if($scope.hospitalPrescriptionNum.timeType == 1){
			startDate = now;
			endDate = now;
		}
		if($scope.hospitalPrescriptionNum.timeType == 2){
			now.setDate(now.getDate()-1);
			startDate = now;
			endDate = now;
		}
		if($scope.hospitalPrescriptionNum.timeType == 3){
			var y = now.getFullYear(), m = now.getMonth();
			startDate = new Date(y, m, 1);
			endDate = new Date(y, m + 1, 0);
		}
		if($scope.hospitalPrescriptionNum.timeType == 4){
			now.setMonth(now.getMonth() -1);
			var y = now.getFullYear(), m = now.getMonth();
			startDate = new Date(y, m, 1);
			endDate = new Date(y, m + 1, 0);
		}
		
		$http({
			method: "GET",
			url: $scope.app.url_prescription+"prescription/getPrescriptionNumByHospital",
			params:{
				startDate: startDate.getTime(),
				endDate: endDate.getTime(),
				size: size
			}
		})
		.success(function(response){
			if(response.code == 1){
				if(response.data.length == 0){
					$scope.hospitalPrescriptionNum.data = [{label:"暂无数据",data:100}];
				}else{
					$scope.hospitalPrescriptionNum.data = response.data;
				}
			}else if(response.code == 4){
				alert(response.msg);
				$state.go('access.signin');
			}else{
				alert(response.msg);
			}
		})
		.error(function(data){
			alert('网络异常，请稍后再试');
		});
		
	}
	
	$scope.refreshDoctorPrescriptionNum = function(){
		var size = 8;//前8的数据，颜色只配置了8种
		
		var startDate;
		var endDate;
		var now = new Date();
		
		if($scope.doctorPrescriptionNum.timeType == 1){
			startDate = now;
			endDate = now;
		}
		if($scope.doctorPrescriptionNum.timeType == 2){
			now.setDate(now.getDate()-1);
			startDate = now;
			endDate = now;
		}
		if($scope.doctorPrescriptionNum.timeType == 3){
			var y = now.getFullYear(), m = now.getMonth();
			startDate = new Date(y, m, 1);
			endDate = new Date(y, m + 1, 0);
		}
		if($scope.doctorPrescriptionNum.timeType == 4){
			now.setMonth(now.getMonth() -1);
			var y = now.getFullYear(), m = now.getMonth();
			startDate = new Date(y, m, 1);
			endDate = new Date(y, m + 1, 0);
		}
		
		$http({
			method: "GET",
			url: $scope.app.url_prescription+"prescription/getPrescriptionNumByDoctor",
			params:{
				startDate: startDate.getTime(),
				endDate: endDate.getTime(),
				size: size
			}
		})
		.success(function(response){
			if(response.code == 1){
				if(response.data.length == 0){
					$scope.doctorPrescriptionNum.data = [{label:"暂无数据",data:100}];
				}else{
					$scope.doctorPrescriptionNum.data = response.data;
				}
			}else if(response.code == 4){
				alert(response.msg);
				$state.go('access.signin');
			}else{
				alert(response.msg);
			}
		})
		.error(function(data){
			alert('网络异常，请稍后再试');
		});
		
	}
	
	$scope.refreshStoreSales = function(){
		var size = 8;//前8的数据，颜色只配置了8种
		
		var startDate;
		var endDate;
		var now = new Date();
		
		if($scope.storeSales.timeType == 1){
			startDate = now;
			endDate = now;
		}
		if($scope.storeSales.timeType == 2){
			now.setDate(now.getDate()-1);
			startDate = now;
			endDate = now;
		}
		if($scope.storeSales.timeType == 3){
			var y = now.getFullYear(), m = now.getMonth();
			startDate = new Date(y, m, 1);
			endDate = new Date(y, m + 1, 0);
		}
		if($scope.storeSales.timeType == 4){
			now.setMonth(now.getMonth() -1);
			var y = now.getFullYear(), m = now.getMonth();
			startDate = new Date(y, m, 1);
			endDate = new Date(y, m + 1, 0);
		}
		
		$http({
			method: "GET",
			url: $scope.app.url_prescription+"record/getSalesByStore",
			params:{
				startDate: startDate.getTime(),
				endDate: endDate.getTime(),
				size: size
			}
		})
		.success(function(response){
			if(response.code == 1){
				if(response.data.length == 0){
					$scope.storeSales.data = [{label:"暂无数据",data:100}];
				}else{
					$scope.storeSales.data = response.data;
				}
			}else if(response.code == 4){
				alert(response.msg);
				$state.go('access.signin');
			}else{
				alert(response.msg);
			}
		})
		.error(function(data){
			alert('网络异常，请稍后再试');
		});
		
	}
	
	$scope.refreshDrugSales = function(){
		var size = 8;//前8的数据，颜色只配置了8种
		
		var startDate;
		var endDate;
		var now = new Date();
		
		if($scope.drugSales.timeType == 1){
			startDate = now;
			endDate = now;
		}
		if($scope.drugSales.timeType == 2){
			now.setDate(now.getDate()-1);
			startDate = now;
			endDate = now;
		}
		if($scope.drugSales.timeType == 3){
			var y = now.getFullYear(), m = now.getMonth();
			startDate = new Date(y, m, 1);
			endDate = new Date(y, m + 1, 0);
		}
		if($scope.drugSales.timeType == 4){
			now.setMonth(now.getMonth() -1);
			var y = now.getFullYear(), m = now.getMonth();
			startDate = new Date(y, m, 1);
			endDate = new Date(y, m + 1, 0);
		}
		
		$http({
			method: "GET",
			url: $scope.app.url_prescription+"record/getSalesByDrug",
			params:{
				startDate: startDate.getTime(),
				endDate: endDate.getTime(),
				size: size
			}
		})
		.success(function(response){
			if(response.code == 1){
				if(response.data.length == 0){
					$scope.drugSales.data = [{label:"暂无数据",data:100}];
				}else{
					$scope.drugSales.data = response.data;
				}
			}else if(response.code == 4){
				alert(response.msg);
				$state.go('access.signin');
			}else{
				alert(response.msg);
			}
		})
		.error(function(data){
			alert('网络异常，请稍后再试');
		});
		
	}
	
	$scope.$watch('$viewContentLoaded', function() {
		$scope.refreshDayIncome();
		$scope.refreshMonthIncome();
		$scope.refreshHospitalPrescriptionNum();
		$scope.refreshDoctorPrescriptionNum();
		$scope.refreshStoreSales();
		$scope.refreshDrugSales();

	});
	
  }]);