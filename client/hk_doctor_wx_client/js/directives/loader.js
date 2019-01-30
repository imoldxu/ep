/**
 * Created by ccf on 17/3/6.
 */

define(['app'], function(app){
    return app.directive('loader',['$window','$state',function($window,$state){

        return{
            restrict: 'E',

            templateUrl: "js/views/Components/loader.html",

            transclude: true,

            replace:true,

            scope : true,

            link: function($scope, element, attrs){



            }
        }
    }])
});
