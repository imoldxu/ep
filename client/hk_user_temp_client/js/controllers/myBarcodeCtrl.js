
define(['weui'], function(weui){

    return ['$scope', '$http', '$window', '$document', '$location','$rootScope','dataVer' ,'$state','$stateParams', function($scope, $http, $window, $document, $location,$rootScope,dataVer,$state,$stateParams){
		
        $scope.barcode = $stateParams.code;

		$scope.showNav = false;

		$scope.goBack = function(){
			$window.history.back();
		}
		
		$scope.is_weixn = function(){
			var ua = $window.navigator.userAgent.toLowerCase();
			if(ua.match(/MicroMessenger/i)=="micromessenger") {
				return true;
			} else {
				return false;
			}
		}
		
		$scope.saveImg = function(){
			
			if($scope.is_weixn()){
				$scope.showNav = true;
				weui.topTips('点击右上角,选择收藏按钮保存处方');
				return false;
			}else{
				var siteName = '电子处方' + $scope.barcode;
				var siteUrl = $location.absUrl();
				//捕获加入收藏过程中的异常				
				try{       
					//判断浏览器是否支持document.all			
					if($document[0].all){                     
						//如果支持则用external方式加入收藏夹              
						$window.external.addFavorite(siteUrl,siteName);                
					 }else if($window.sidebar){                      
						//如果支持window.sidebar，则用下列方式加入收藏夹  
						$window.sidebar.addPanel(siteName, siteUrl,'');         
					}else{
						weui.alert("请手动添加书签");
					}							
				}catch (e){          
					weui.alert("请手动添加书签");   
				}				
			}
			
			/**
			var outElement = $document.find('#out_img')[0];
			$window.html2canvas(outElement).then(canvas => {
				
				var filename = '电子处方' + $scope.code + '.png';
				
				var imgData = canvas.toDataURL('png');
				imgData = imgData.replace('image/png','image/octet-stream');
				//var save_link = $window.document.createElementNS('ly', 'a');
				//save_link.href = imgData;
				//save_link.download = filename;
				$window.location.href = imgData;
   
				//var event = $window.document.createEvent('MouseEvents');
				//event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
				//save_link.dispatchEvent(event);
				
			});
			*/
			return false;
		}
		
		$scope.gotoSearch = function(){
			dataVer.put('locationInfo', null);
					
			$state.go('result');
		}
		
		$scope.getPrescription = function(){
			var loading = weui.loading("加载中...");
			
			$http({
                method: 'get',
                url: URL3+'prescription/receiveonly',
                requestType: 'json',
                params: {
                    barcode: $scope.barcode
                }
            })
			.success(function(resp){
				
				loading.hide();

                if (resp.code == 1){

                    dataVer.put('prescriptionInfo' ,resp.data);
					
				} else{
					weui.confirm("获取失败，是否重试",function(){
						$scope.getPrescription();
					}, function(){
						$window.history.back();
					});
					
					
				}

            })
			.error(function(data){
				loading.hide();
		
				weui.confirm("系统服务异常，是否重试",function(){
						$scope.getPrescription();
					}, function(){
						$window.history.back();
					});
			})
		}
		
		$scope.$watch('$viewContentLoaded', function() {
			// 页面加载完执行
			// 获取处方
			$scope.getPrescription();
		}); 
    }];

});
