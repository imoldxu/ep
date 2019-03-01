
define(['jquery','app','angular','weui'], function($,app,angular,weui){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state','$sce','$stateParams', function($scope, $http, $window,$location,$rootScope,dataVer,$state,$sce,$stateParams){

		$scope.address = JSON.parse($stateParams.address);

		$scope.url = 'https://apis.map.qq.com/tools/poimarker?type=0&marker=coord:'+$scope.address.latitude+','+$scope.address.longitude+';title:'+$scope.address.name+';addr:'+$scope.address.addr+'&key=7E2BZ-D5ECJ-W6OFU-FVZ7L-SBMR3-WXBZ2&referer=myapp';

		$scope.navUrl = $sce.trustAsResourceUrl($scope.url);


    }];

});
