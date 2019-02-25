
define(['angular','weui'], function(angular,weui){

    return ['$scope', '$compile', '$http', '$window','$location','$rootScope','dataVer' ,'$state','$sce','$stateParams', function($scope, $compile, $http, $window,$location,$rootScope,dataVer,$state,$sce,$stateParams){

		//$scope.articleObj = dataVer.get('articleInfo');
		$scope.articleId = $stateParams.articleId;

		$scope.init = function (){

			//$scope.title = $scope.articleObj.title;

			//$scope.articleUrl = null;
			
			//if($scope.articleObj.url != null && $scope.articleObj.url.length > 0){
				//$scope.articleUrl = $sce.trustAsResourceUrl($scope.articleObj.url);
			//}

			if($scope.articleObj.content != null && $scope.articleObj.content.length > 0){
				var template = angular.element($scope.articleObj.content);
				var contentElement = $compile(template)($scope);
				var parentElement = angular.element('#article_content');
				parentElement.append(contentElement);
			}
		}

		$scope.$watch('$viewContentLoaded', function() {

			var loading = weui.loading('加载中...');

			$http({
                method: 'get',
                url: URL+'article/getArticleById',
                requestType: 'json',
                params: {
					articleId: $scope.articleId
                }
            })
			.success(function(resp){

				loading.hide();

                if (resp.code == 1){

					console.log(resp.data);

					$scope.articleObj = resp.data;
					
					$scope.init();
					
                } else if(resp.code == 4){
					weui.alert(resp.msg);
					
					$state.go('login');
				} else{
					weui.alert(resp.msg);
				}

            })
			.error(function(data){
				
				loading.hide();
				
				weui.alert('系统服务异常，请联系管理员');
				
			})


		});

    }];

});
