
define([], function(){

    return ['$scope', '$http', '$cookieStore','$location','$rootScope','dataVer' ,'$state', 'Md5', function($scope, $http, $cookieStore,$location,$rootScope,dataVer,$state,Md5){

		$scope.storeInfo = dataVer.get('sellerInfo');
		
		$scope.pwdModel = false;
	
        //默认值
		$scope.nextpage = function(page){
			
			$state.go(page);
		}
		
		$scope.closePwdMod = function(){
			$scope.pwdModel = false;
		}
		
		$scope.openPwdModel = function(){
			$scope.pwdModel = true;
			
			$scope.newpwd = '';
			
			$scope.oldpwd = '';
			
			$scope.newpwd2 = '';

		}
		
		$scope.modifyPwd = function(oldpwd, newpwd, newpwd2){
		   if(oldpwd=='' || newpwd=='' || newpwd2==''){
		       alert("请输入新旧密码");
			   return false;
		   }
		   if(newpwd != newpwd2){
		       alert("两次输入的新密码必须一致");
			   return false;
		   }
		   
		   $rootScope.myloader = true;
		   
		   //oldpwd = Md5.b64_hmac_md5("hk",oldpwd);//使用md5对密码加密,并转换为HEX
		   //newpwd = Md5.b64_hmac_md5("hk",newpwd);
		   
		   $http({
                method: 'post',
                url: URL+'seller/modifyPwd',
				data: {
					oldPassword: oldpwd,
					newPassword: newpwd
                }
            })
            .success(function(resp){

				$rootScope.myloader = false;

                if (resp.code == 1){

					alert("修改成功");
					
					$scope.closePwdMod();
										
                }else if(resp.code == 4){
					alert(resp.msg);
					
					$scope.closePwdMod();
					
					$state.go('login');
					
				}else{
					alert(resp.msg);
				
				}

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			});
		}
		
		$scope.logout = function(){
			
			$rootScope.myloader = true;
		
			$http({
                method: 'get',
                url: URL+'seller/logout',
				requestType: 'json',
                params: { 
                }
            })
            .success(function(data){

				$rootScope.myloader = false;

                if (data.code == 1){

					dataVer.put('sellerInfo', null);
					
					$state.go('login');
 
                }else{
				
					alert(data.msg);
				
				}

            })
			.error(function(data){
				
				$rootScope.myloader = false;
				
				alert('系统服务异常，请联系管理员');
				
			});
		}

    }];

});

