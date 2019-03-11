/**
 * Created by ccf on 16/11/16.
 */


define(['jquery','app'], function ($,app) {
    app.directive('wheader', ['$window','$state',function ($window, $state) {
        return {

            restrict: 'E',

            templateUrl: "js/views/Components/wheader.html",

            transclude: true,

            replace:true,
			link: function($scope, iElements, iAttrs){
       
				$scope.title = iAttrs.title;

                $scope.backShow = iAttrs.backshow == 'false'? false : true || true;

				$scope.rightIcon = iAttrs.righticon || null;

                $scope.goBack = function(){
					if($window.history.length>1){//chromeȱʡ����1
						$window.history.back();
					}else{
						$state.go('home');//��historyû�������򷵻�homeҳ
					}
				};

            }
        }
    }])
});
