
define(['weui'], function(weui){

    return ['$scope', '$http','$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http,$window, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

        //默认值
		$scope.phone = dataVer.get('loginname');
		
		$scope.pwd = dataVer.get('password');

        $scope.login = function( phone, pwd){

            if (phone == undefined || phone == ''){
                weui.topTips('请输入登录手机号', 3000);
				return false;
            }
			if (pwd == undefined || pwd == '' ){
                weui.topTips('请输入登录密码', 3000);
				return false;
            }
			
			//FIXME: 暂时屏蔽
			//pwd = Md5.b64_hmac_md5("hk",pwd);//使用md5对密码加密,并转换为HEX
			
			var loading = weui.loading('登陆中...');
			
            $http({
                method: 'post',
                url: URL+'user/login',
                data: {
                    phone: phone,
					password: pwd
                }
            })
            .success(function(resp){
				
				loading.hide();

                if (resp.code == 1){

					dataVer.put('loginname', phone);
		
					dataVer.put('password', pwd);

                    dataVer.put('userInfo',resp.data);
					
					dataVer.put('homestate',{tabId: '#tab1', pageIndex:1, pList:[], isfinish: false, isloading: true, articleList:[], articlePageIndex:1, articleisfinish: false, articleisloading: true});//清空首页状态
					weui.toast('登陆成功', 1000);
					
					$location.path('/wxQrcode').replace();
						
					//$location.path('/home').replace();//测试屏蔽
					
                    return false;

                }else{

					weui.alert(resp.msg);

					console.log(resp);
				}

            })
			.error(function(data){
				
				loading.hide();
				
				weui.alert('系统服务异常，请联系管理员');
				
			})
        }
		
		$scope.gotoRegister = function(){
			$state.go('register');
		}
		
		$scope.recievePrescription = function(barcode, subscribe){
			var loading = weui.loading('正在接收处方...');
			
			$http({
				method: 'post',
				url: URL3 + 'prescription/recieve',
				data: {
					barcode: barcode
				}
			})
			.success(function (resp) {

				loading.hide();

				if (resp.code == 1) {
				
					dataVer.put('homestate', {tabId: '#tab2', pageIndex:1, pList:[], isfinish: false, isloading: true, articleList:[], articlePageIndex:1, articleisfinish: false, articleisloading: true});//清空首页状态
				
					weui.toast('接收成功',1000);
				
					if(subscribe == 0 ){
						$location.path('/wxQrcode').replace();
						return false;
					}
				
					
					$location.path('/home').replace();
					
					return false;
				} else if(resp.code == 4){
					weui.alert(resp.msg);
					$state.go('login');
				}else{
					weui.alert('接收失败,请重新扫描');
				}
			})
			.error(function(data){
				loading.hide();
				weui.alert('系统异常，请联系管理员');
			})
		}
		
		$scope.getPropertie = function (name) {
			//方法1
			var ret =$location.search()[name];
			return ret;
	
			//方法二
			/**var url = $location.absUrl();
			var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
			var matcher = pattern.exec(url);
			var items = null;
			if (null != matcher) {
				try {
					items = decodeURIComponent(decodeURIComponent(matcher[1]));
				} catch (e) {
					try {
						items = decodeURIComponent(matcher[1]);
					} catch (e) {
						items = matcher[1];
					}
				}
			}
			console.log(items)
			return items;*/
		};
    
    
    
        $scope.wxLogin = function () {
    
			var loading = weui.loading('登陆中');
			
			var code = $scope.getPropertie('code');
			
			$http({
				method: 'post',
				url: URL + 'user/loginByWx',
				params: {
					wxCode: code
				}
			})
			.success(function (resp) {

				loading.hide();

				if (resp.code == 1) {

					dataVer.put('userInfo',resp.data);
					
					var barcode = $scope.getPropertie('state');
					if(barcode==0){
						
						dataVer.put('homestate', {tabId: '#tab1', pageIndex:1, pList:[], isfinish: false, isloading: true, articleList:[], articlePageIndex:1, articleisfinish: false, articleisloading: true});//清空首页状态
						
						if(resp.data.subscribe == 0){
							$location.path('/wxQrcode').replace();
							return false;
						}
						
						weui.toast('登陆成功',1000);
						
						$location.path('/home').replace();
					}else{
						$scope.recievePrescription(barcode, resp.data.subscribe);
					}
					return false
				}else{
					weui.alert(resp.msg);
				}
			})
			.error(function(data){
				loading.hide();
				weui.alert('系统异常，请联系管理员');
			});

        }
		
		$scope.$watch('$viewContentLoaded', function() {
			//页面加载完，判断页面URL上是否有code，有则直接发起微信登陆，没有则用户点击微信登陆按钮触发重定向到该页面
			var code = $scope.getPropertie('code');
			if(code != null && code.length>0){
				$scope.wxLogin();
			}
					
		});
		
    }];

});
