
define(['jquery','app','angular','weui','oss'], function($, app, angular, weui, oss){

    return ['$scope', '$http','$window', '$document', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window, $document, $cookieStore,$location,$rootScope,dataVer,$state,AppCtrl,ossService){

		$scope.doctorObj = dataVer.get('doctorInfo');

		$scope.ok = function(){
			var signature = $scope.accept();
			if(signature.isEmpty){
				weui.topTips("请手写签名", 3000);
			}else{
				if(signature.dataUrl == undefined || signature.dataUrl == null){
					weui.topTips("请手写签名", 3000);
					return false;
				}
				$scope.uploadDataUrl(signature.dataUrl);
			}
		}
		
		$scope.toBlob = function(urlData,fileType){
			var bytes = $window.atob(urlData),
			n=bytes.length,
			u8arr=new Uint8Array(n);
			while(n--){
				u8arr[n]=bytes.charCodeAt(n);
			}
			return new Blob([u8arr],{type:fileType});
		}
		
		$scope.uploadDataUrl = function(dataUrl){
			// 图像数据 (e.g. data:image/png;base64,iVBOR...yssDuN70DiAAAAABJRU5ErkJggg==)
			var base64 = dataUrl.split(',')[1];
			var fileType = dataUrl.split(';')[0].split(':')[1];
			
			if(base64==null || base64.length==0){
				weui.topTips("请手写签名", 3000);
				return false;
			}
			
			// base64转blob
			var blob = $scope.toBlob(base64,fileType);
			
			var reader = new FileReader();
			reader.readAsArrayBuffer(blob);
			reader.onload =  function (event) {
			
				var content = new Buffer(event.target.result);
			
				$scope.applyTokenDo($scope.uploadContent, content);
			}
		}
		
		$scope.uploadContent = function (client, content) {
			var loading = weui.loading('上传中...');
			var timestamp = (new Date()).getTime();
			var sigImg = "img/"+$scope.doctorObj.id+timestamp+".sig";//加时间戳到文件名中，保留医生设置过的签名，避免修改的签名覆盖之前的签名
			return client.put(sigImg, content).then(function (res) {
				console.log(res);
				loading.hide();
				weui.toast('上传成功',3000);
				var sigurl = client.signatureUrl(sigImg);
				$scope.doctorObj.signatureurl = sigurl;
				dataVer.put('doctorInfo', $scope.doctorObj);//在每次进入修改页之前，应该使用doctor数据初始化signatureurl
				$window.history.back();
				//$state.go('updateInfo');
			}, function(resp){
				loading.hide();
				weui.alert('上传失败,请稍后再试');
			});
		};
		
		//var appServer = "http://127.0.0.1:9210";
		var bucket = 'zumeng';
		var region = 'oss-cn-shenzhen';

		console.log(oss);

		var urllib = oss.urllib;
		var Buffer = oss.Buffer;
		//var OSS = oss.Wrapper;
		//var STS = oss.STS;

		$scope.applyTokenDo = function(func, content) {
			var loading = weui.loading('授权中...');
			var url = appServer;
			return urllib.request(url, {
				method: 'GET'
			}).then(function (result) {
				loading.hide();
				var creds = JSON.parse(result.data);
				var client = new oss({
					region: region,
					accessKeyId: creds.AccessKeyId,
					accessKeySecret: creds.AccessKeySecret,
					stsToken: creds.SecurityToken,
					bucket: bucket
				});
				
				return func(client, content);
			}, function(reason){
				loading.hide();
				weui.alert('服务器异常,请稍后再试');
			});
		}
	
		/**
		强制切换横屏，canvas切换后悔乱画
		$scope.handleLandOrPortrait	= function() {
				var width =  $document.context.documentElement.clientWidth;
				var height =   $document.context.documentElement.clientHeight;
				$land =  $('.land');
				if( width > height ){
				   
					$land.width(width);
					$land.height(height);
					$land.css('top',  0 );
					$land.css('left',  0 );
					$land.css('transform' , 'none');
					$land.css('transform-origin' , '50% 50%');
				}else{
					$land.width(height);
					$land.height(width);
					$land.css('top',  (height-width)/2 );
					$land.css('left',  0-(height-width)/2 );
					$land.css('transform' , 'rotate(90deg)');
					$land.css('transform-origin' , '50% 50%');
				 }
				
			}
		
		$scope.$watch('$viewContentLoaded', function() {
			var width = $document.context.documentElement.clientWidth;
			var height = $document.context.documentElement.clientHeight;
			if( width < height ){
				console.log(width + " " + height);
				$land =  $('.land');
				$land.width(height);
				$land.height(width);
				$land.css('top',  (height-width)/2 );
				$land.css('left',  0-(height-width)/2 );
				$land.css('transform' , 'rotate(90deg)');
				$land.css('transform-origin' , '50% 50%');
			}
			
			var evt = "onorientationchange" in $window ? "orientationchange" : "resize";
			
			$window.addEventListener(evt, $scope.handleLandOrPortrait, false);
		});
		
		$scope.$on("$destroy", function() {
		   var evt = "onorientationchange" in $window ? "orientationchange" : "resize";
			
		   $window.removeEventListener(evt, $scope.handleLandOrPortrait, false);
		})
		*/
    }];

});
