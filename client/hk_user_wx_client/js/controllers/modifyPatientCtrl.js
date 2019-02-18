
define(['weui'], function(weui){

    return ['$scope', '$http', '$window','$cookieStore','$location','$rootScope','dataVer' ,'$state', '$stateParams', function($scope, $http, $window, $cookieStore,$location,$rootScope,dataVer,$state,$stateParams){

		$scope.patientObj = JSON.parse($stateParams.patient);
        //默认值
        $scope.add = function(pid, name, sex, idcardnum, phone){

            if (name == undefined || name == ''){
                weui.topTips('请输入姓名', 3000);
				return false;
            }
			if (sex == undefined || sex == ''){
                weui.topTips('请选择性别', 3000);
				return false;
            }
			if (idcardnum == undefined || idcardnum == ''){
                weui.topTips('请输入身份证号', 3000);
				return false;
            }
				
			var loading = weui.loading("提交中...");
			
            $http({
                method: 'post',
                url: URL+'user/updatePatient',
                data: {
					pid: pid,
					name: name,
                    sex: sex,
					phone: phone,
					idcardtype: 1,
					idcardnum: idcardnum
               }
            })
            .success(function(resp){
				
				loading.hide();
				
                if (resp.code == 1){

                    weui.toast('添加成功', 3000);
					
					//dataVer.put('doctorInfo', resp.data);
					
					$scope.goBack();

                    return false;

                } else{

					weui.alert(resp.msg);

					console.log(resp)
				}

            })
			.error(function(data){
				
				loading.hide();
				
				weui.alert('系统服务异常，请联系管理员');
				
			})
        }

    }];

});
