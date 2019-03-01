
define(['angular','layer','weui'], function(angular,layer,weui){

    return ['$scope', '$http', '$window','$location','$rootScope','dataVer' ,'$state','$stateParams', function($scope, $http, $window,$location,$rootScope,dataVer,$state,$stateParams){

        //默认值
		$scope.pid = $stateParams.pid;
		$scope.content = "";
		
		$scope.addContent = function(content){
			if($scope.content.length>0){
				$scope.content = $scope.content.concat(' '+content);
			}else{
				$scope.content = content;
			}
		}
		
		$scope.commit = function(){
		
			var loading = weui.loading('提交中...');
			
			$http({
				method: 'post',

				url: URL3+'prescription/commitComment',

				requestType: 'json',

				data: {

					pid: $scope.pid,
					
					star: $scope.ratingValue,
				
					content: $scope.content,
				}
			})
			.success(function(resp){
				
				loading.hide();
				
				if (resp.code == 1){

					weui.toast('评论成功',1000);
					
					$window.history.go(-1);
					
				}else if(resp.code == 4){
					weui.alert(resp.msg);
					
					$state.go('login');
				}else{
				
					weui.alert(resp.msg);

					console.log(resp);
				}
			
			})
			.error(function(data){
			
				loading.hide();
				
				weui.alert('系统服务异常，请联系管理员');
			
			});
           
        } 
		
    }];

});
