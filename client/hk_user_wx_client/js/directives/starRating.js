/**
 * Created by ccf on 16/11/16.
 */


define(['app'], function (app) {
    app.directive('starrating', function () {
		return {
			require : '?ngModel', // ?ngModel
			restrict : 'E',
			replace : true,
			templateUrl : 'js/views/Components/starRating.html',
			scope: {ngModel : '='},
			link: function ($scope, element, attrs, ngModel) {
				$scope.myStars = [1,2,3,4,5];
				$scope.clickCnt = 5;
				$scope.$watch('ngModel', function(newValue) {
					var dataList = newValue;
					console.log(dataList);
					if(!dataList) return;
					$scope.myStar = dataList;
					$scope.clickCnt = dataList;
					$scope.hoverCnt = dataList;
				})
				$scope.stars = function (myStar) {
					$scope.clickCnt = myStar;
					ngModel.$setViewValue(myStar);
				}
				
				$scope.mouseoverStar = function (myStar) {
					$scope.hoverCnt = myStar;
					ngModel.$setViewValue(myStar);
				}
				$scope.mouseleaveStar = function (myStar) {
					$scope.hoverCnt = myStar;
					ngModel.$setViewValue(myStar);
				}
				$scope.stars(5);
			}
		}
	});
});
