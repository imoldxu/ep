/**
 * Created by ccf on 16/11/16.
 */


define(['jquery','app'], function ($,app) {
    app.directive('wheader', ['$window',function ($window) {
        return {

            restrict: 'E',

            templateUrl: "js/views/Components/wheader.html",

            transclude: true,

            replace:true,


            link: function($scope, iElements, iAttrs){
       
				$scope.header = {};
	   
				$scope.header.title = iAttrs.title;

                $scope.header.backShow = iAttrs.backshow == 'false'? false : true || true;

				//$scope.todo = iAttrs.rightfunc;

				$scope.header.rightIcon = iAttrs.righticon || null;

                $scope.goBack = function(){
					$window.history.back();
				};

            }
        }
    }])
});
