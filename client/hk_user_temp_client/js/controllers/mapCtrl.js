
define(['jquery','app','angular','weui'], function($,app,angular,weui){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window,$location,$rootScope,dataVer,$state){

		$scope.location = dataVer.get('locationInfo') || {latitude:39.916527, longitude:116.397128, place:''};

		$scope.handleMessage = function(event) {
			// 接收位置信息，用户选择确认位置点后选点组件会触发该事件，回传用户的位置信息
			var loc = event.data;
			if (loc && loc.module == 'locationPicker') {//防止其他应用也会向该页面post信息，需判断module是否为'locationPicker'
				console.log('location', loc);
				$scope.location.latitude = loc.latlng.lat;
				$scope.location.longitude = loc.latlng.lng;
				$scope.location.place = loc.poiname;
				
				dataVer.put('locationInfo',$scope.location);
				
				$window.history.back();
			}
		}

		$window.addEventListener('message', $scope.handleMessage, false);

		$scope.$on("$destroy", function() {
		   $window.removeEventListener('message', $scope.handleMessage, false);
		})
    }];

});
