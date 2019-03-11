'use strict';

/* Controllers */

  // ArticlemCtrl controller
app.controller('ArticlemCtrl', ['$scope','$http','$modal','$state','$stateParams', '$log', '$compile', function($scope, $http, $modal, $state, $stateParams, $log, $compile) {
    
	$scope.articleList = [];
	$scope.hasNextPage = false;
	$scope.pageIndex = 1; 
	$scope.title = '';
	$scope.selectedArticle = null;
	
	$scope.changeState = function(index){
		var state = $scope.articleList[index].state == 1 ? 0 : 1;
		$http({
		  method: "POST",  
		  url: $scope.app.url_user+'article/changeState',
		  params: {
			  articleId: $scope.articleList[index].articleid,
			  state: state
			}
		  })
		  .success(function(response) {
			if (response.code == 1) {
				$scope.articleList[index].state = state;
				return false;
			}else if(response.code == 4){
				alert(response.msg);
				$state.go('access.signin');
				return false;
			}else{
				alert(response.msg);
				return false;
			}
		  })
		  .error(function(x) {
				alert('网络异常,请稍后再试');
		  });

	}
	
	$scope.preview = function(index){
		$scope.selectedArticle = $scope.articleList[index];
		
		$http({
		  method: "GET",  
		  url: $scope.app.url_user+'article/getArticleById',
		  params: {
			  articleId: $scope.selectedArticle.articleid
			}
		  })
		  .success(function(response) {
			if (response.code == 1) {
				$scope.selectedArticle = response.data;
				if($scope.selectedArticle.content != null && $scope.selectedArticle.content.length > 0){
					var template = angular.element($scope.selectedArticle.content);
					var contentElement = $compile(template)($scope);
					var parentElement = angular.element('#article_content');
					var children = parentElement.children();
					//for(var i =0; i<children.length;i++){
						children.remove();
					//}
					parentElement.append(contentElement);
				}
				return false;
			}else if(response.code == 4){
				alert(response.msg);
				$state.go('access.signin');
				return false;
			}else{
				alert(response.msg);
				return false;
			}
		  })
		  .error(function(x) {
				alert('网络异常,请稍后再试');
		  });
		
		
	}
	
	$scope.nextPage = function(){
		$scope.pageIndex = $scope.pageIndex+1;
		getArticleList();
	}
	
	$scope.prePage = function(){
		$scope.pageIndex = $scope.pageIndex-1;
		getArticleList();
	}
	
	$scope.search = function() {
		
	  $scope.pageIndex = 1;
	  $scope.articleList = [];
	  $scope.hasNextPage = false;
	
	  $scope.getArticleList();
	}
	
	$scope.getArticleList = function(){
      $http({
		  method: "GET",  
		  url: $scope.app.url_user+'article/getArticles',
		  params: {title: $scope.title,
				   pageIndex: $scope.pageIndex,
				   pageSize: 20}
	  })
      .success(function(response) {
        if (response.code == 1) {
			$scope.articleList = response.data;
			if($scope.articleList.length == 20){
				$scope.hasNextPage = true;
			}else{
				$scope.hasNextPage = false;
			}
			return false;
        }else if(response.code == 4){
			alert(response.msg);
			$state.go('access.signin');
			return false;
		}else{
			alert(response.msg);
			return false;
        }
      })
	  .error(function(x) {
            alert('网络异常,请稍后再试');
      });
    };
	
	$scope.add = function (size) {
      var modalInstance = $modal.open({
        templateUrl: 'addArticleModalContent.html',
        controller: 'AddArticleModalInstanceCtrl',
        size: size,
        resolve: {}
      });

      modalInstance.result.then(function (data) {
        $scope.articleList = [];
		$scope.articleList.push(data);
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };
	
	$scope.edit = function (index, size) {
        
		
		$http({
		  method: "GET",  
		  url: $scope.app.url_user+'article/getArticleById',
		  params: {
			  articleId: $scope.articleList[index].articleid
			}
		  })
		  .success(function(response) {
			if (response.code == 1) {
				var modalInstance = $modal.open({
					templateUrl: 'modifyArticleModalContent.html',
					controller: 'ModifyArticleModalInstanceCtrl',
					size: size,
					resolve: {
					  article: function () {
						return angular.copy(response.data);
					  },
					}
				  });

				  modalInstance.result.then(function (data) {
					$scope.articleList[index] = data;
				  }, function () {
					$log.info('Modal dismissed at: ' + new Date());
				  });				
				return false;
			}else if(response.code == 4){
				alert(response.msg);
				$state.go('access.signin');
				return false;
			}else{
				alert(response.msg);
				return false;
			}
		  })
		  .error(function(x) {
				alert('网络异常,请稍后再试');
		  });

    };
	
	$scope.dateFormat = function(time, format){
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
	
	$scope.getDateDiff = function(dateTimeStamp) {
		var minute = 1000 * 60;
		var hour = minute * 60;
		var day = hour * 24;
		var halfamonth = day * 15;
		var month = day * 30;
		var now = new Date().getTime();
		var diffValue = now - dateTimeStamp;
		if (diffValue < 0) {
			//若日期不符则弹窗口告之,结束日期不能小于开始日期！
		}
		var monthC = diffValue / month;
		var weekC = diffValue / (7 * day);
		var dayC = diffValue / day;
		var hourC = diffValue / hour;
		var minC = diffValue / minute;
		var result = "最新";
		if (monthC >= 1) {
			result = parseInt(monthC) + "个月前";
		}
		else if (weekC >= 1) {
			result = parseInt(weekC) + "周前";
		}
		else if (dayC >= 1) {
			result = parseInt(dayC) + "天前";
		}
		else if (hourC >= 1) {
			result = parseInt(hourC) + "个小时前";
		}
		else if (minC >= 1) {
			result = parseInt(minC) + "分钟前";
		} else
			result = "最新";
		return result;
	}
	
	$scope.$watch('$viewContentLoaded', function() {
		$scope.getArticleList();
	});
	
	
  }])
 ;