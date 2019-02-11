
define(['app','angular','oss'], function(app, angular, oss){

    return ['$scope', '$http','$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state,AppCtrl,ossService){

		$scope.doctorObj = dataVer.get('doctorInfo');

		$scope.ok = function(){
			var signature = $scope.accept();
			if(signature.isEmpty){
				alert("请手写签名");
			}else{
				$scope.uploadDataUrl(signature.dataUrl);
			}
		}
		
		$scope.goBack = function() {
			$window.history.back();
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
			var sigImg = "img/"+$scope.doctorObj.id+".sig";
			return client.put(sigImg, content).then(function (res) {
				console.log(res);
				var sigurl = client.signatureUrl(sigImg);
				$scope.doctorObj.signatureurl = sigurl;
				dataVer.put('doctorInfo', $scope.doctorObj);//在每次进入修改页之前，应该使用doctor数据初始化signatureurl
				$state.go('updateInfo');
			});
		};
		
		var appServer = "http://127.0.0.1:9210";
		var bucket = 'zumeng';
		var region = 'oss-cn-shenzhen';

		console.log(oss);

		var urllib = oss.urllib;
		var Buffer = oss.Buffer;
		//var OSS = oss.Wrapper;
		//var STS = oss.STS;

		$scope.applyTokenDo = function(func, content) {
			var url = appServer;
			return urllib.request(url, {
				method: 'GET'
			}).then(function (result) {
				var creds = JSON.parse(result.data);
				var client = new oss({
					region: region,
					accessKeyId: creds.AccessKeyId,
					accessKeySecret: creds.AccessKeySecret,
					stsToken: creds.SecurityToken,
					bucket: bucket
				});
				
				return func(client, content);
			});
		}
    }];

});
