
define(['jquery','weui'], function($,weui){

    return ['$scope', '$http','$window', '$cookieStore','$location','$rootScope','dataVer' ,'$state','$stateParams', function($scope, $http,$window, $cookieStore,$location,$rootScope,dataVer,$state,$stateParams){

		$(function(){
			$('.weui-tabbar__item').on('click', function () {
				$(this).addClass('weui-bar__item_on').siblings().removeClass('weui-bar__item_on');
				var tabId = $(this).attr('id');
			    $('.weui-tab__panel').find(tabId).show().siblings().hide();	  
				$scope.$apply(
					function(){
					  $scope.state.tabId = tabId;
						
					  if(tabId == '#tab1'){
						$scope.title = "资讯";
						$scope.rightIcon = null;
						$scope.header_right_function = null;
					  }
					  if(tabId == '#tab2'){
						$scope.title = "我的处方";
						$scope.rightIcon = "./img/scan.png";
						$scope.header_right_function = $scope.scan;
					  }
					  if(tabId == '#tab3'){
						$scope.title = "患者";
						$scope.rightIcon = "./img/add2.png";
						$scope.header_right_function = $scope.addPatient;
					  }
					  if(tabId == '#tab4'){
						$scope.title = "我";
						$scope.rightIcon = null;
						$scope.header_right_function = null;
					  }
					});
				});
		});

        //默认值
		$scope.userObj = dataVer.get('userInfo');
		
		$scope.state = dataVer.get('homestate'); //初始化
			
		$scope.patientList = [];

		//文章相关接口
		$scope.refreshArticle = function(){
		
			$scope.state.articlePageIndex = 1;
			$scope.state.articleList = [];
			$scope.getArticleList();
		}
		
		$scope.showArticle = function(index){
			var article = $scope.state.articleList[index];
			//dataVer.put('articleInfo', article);			
			dataVer.put('homestate', $scope.state);
			$state.go('article', {articleId: article.articleid});
			//$window.location = article.url;
		}
		
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
		
		$scope.getArticleList = function(){

			$scope.state.articleisloading = true;

            $http({
                method: 'get',
                url: URL+'article/getArticleList',
                requestType: 'json',
                params: {
					pageIndex: $scope.state.articlePageIndex,
					pageSize: 10
                }
            })
			.success(function(resp){

				$scope.state.articleisloading = false;

                if (resp.code == 1){

					if(resp.data.length != 10){
						$scope.state.articleisfinish = true;
					}else{
						$scope.state.articleisfinish = false;
					}

					$scope.state.articlePageIndex = $scope.state.articlePageIndex + 1; 

					//console.log(resp.data);

					
					console.log(resp.data);

					$scope.state.articleList = $scope.state.articleList.concat(resp.data);
					
                } else if(resp.code == 4){
					weui.alert(resp.msg);
					
					$state.go('login');
				} else{
					weui.alert(resp.msg);
				}

            })
			.error(function(data){
				
				$scope.state.articleisloading = false;
				
				weui.alert('系统服务异常，请联系管理员');
				
			})

        };


		
		$scope.gotoPage = function(page){
			dataVer.put('homestate', $scope.state);
			
			$state.go(page);
		}
 
		$scope.logout = function(){
			
			var loading = weui.loading('登出中...');
			
			$http({
                method: 'get',
                url: URL+'user/logout',
				requestType: 'json',
                params: { 
                }
            })
            .success(function(resp){

				loading.hide();

                if (resp.code == 1){

					dataVer.put('userInfo',{});
			
					$state.go('login');

                }else{
				
					weui.alert(resp.msg);
				
				}

            })
			.error(function(data){
				
				loading.hide();
				
				weui.alert('系统服务异常，请联系管理员');
				
			});
		
		}

		//患者相关接口
		$scope.getPatientList = function(){

            $http({
                method: 'get',
                url: URL+'user/getMyPatientList',
                requestType: 'json',
                params: {
                }
            })
			.success(function(resp){

                if (resp.code == 1){

					console.log(resp.data);

					$scope.patientList = resp.data;
					
                } else if(resp.code == 4){
					weui.alert(resp.msg);
					
					$state.go('login');
				} else{
					weui.alert(resp.msg);
				}

            })
			.error(function(data){
				
				weui.alert('系统服务异常，请联系管理员');
				
			})

        };
		
		$scope.addPatient = function(){
			dataVer.put('homestate', $scope.state);
			$state.go('addPatient');
		}
		
		$scope.modifyPatient = function(index){
			dataVer.put('homestate', $scope.state);
			var p = JSON.stringify($scope.patientList[index]);
			$state.go('modifyPatient', {patient: p});
		}
		
		$scope.showPatientCode = function(barcode){
			dataVer.put('homestate', $scope.state);
			$state.go('showPatientCode', {code: barcode});
		}
		
		$scope.delPatient = function(index, pid){

			var loading = weui.loading('删除中...');

            $http({
                method: 'post',
                url: URL+'user/delPatient',
                requestType: 'json',
                data: {
					pid: pid
                }
            })
			.success(function(resp){

				loading.hide();

                if (resp.code == 1){
					
					weui.toast('删除成功',3000);
					
					$scope.patientList.splice(index,1);
					
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

        };

		//处方相关接口
        $scope.scan = function(){
			$window.wx.ready(function(){
				$window.wx.scanQRCode({
					needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
					scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
					success: function (res) {
						var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
						//解析出url中的barcode
						var pattern = new RegExp("[?&]" + state + "\=([^&]+)", "g");
						var code = pattern.exec(result);
						
						if(code==null){
							weui.alert('请扫描正确的二维码获取处方');
							return false;
						}
						
						var loading = weui.loading('获取中...');
				
						$http({
							method: 'post',
							url: URL3+'prescription/recieve',
							requestType: 'json',
							data: {
								barcode: code
							}
						})
						.success(function(resp){

							loading.hide();

							if (resp.code == 1){

								dataVer.put('homestate', $scope.state);
						
								dataVer.put('prescriptionInfo', resp.data);
						
								$state.go('prescription');

							}else if(resp.code == 4){
								weui.alert(resp.msg);
								$state.go('login');
							}else{
								weui.alert(resp.msg);
								return false;
							}

						})
						.error(function(data){
							
							loading.hide();
							
							weui.alert('系统服务异常，请联系管理员');
							
						});
					}
				});
			});
		}
		
		$scope.showPrescriptionCode = function(barcode){
			dataVer.put('homestate', $scope.state);
			$state.go('myBarcode', {code: barcode});
		}
		
		$scope.loadMore = function(){

			$scope.state.isloading = true;

            $http({
                method: 'get',
                url: URL3+'prescription/getUserPrescriptionList',
                requestType: 'json',
                params: {
                    pageIndex: $scope.state.pageIndex,
					pageSize: 10
                }
            })
			.success(function(resp){

				$scope.state.isloading = false;

                if (resp.code == 1){

					if(resp.data.length != 10){
						$scope.state.isfinish = true;
					}

					$scope.state.pageIndex = $scope.state.pageIndex + 1; 

					//console.log(resp.data);

					$scope.state.pList = $scope.state.pList.concat(resp.data);
					
					//console.log($scope.state.pList);
					
                } else if(resp.code == 4){
					weui.alert(resp.msg);
					
					$state.go('login');
				} else{
					weui.alert(resp.msg);
				}

            })
			.error(function(data){
				
				$scope.state.isloading = false;
				
				weui.alert('系统服务异常，请联系管理员');
				
			})

        };

		
		$scope.gotoDetail = function(pid){
			
			var loading = weui.loading("加载中...");
			
			$http({
                method: 'get',
                url: URL3+'prescription/getUserPrescriptionByID',
                requestType: 'json',
                params: {
                    pid: pid
                }
            })
			.success(function(resp){
				
				loading.hide();

                if (resp.code == 1){

                    var prescription = resp.data;
					
					dataVer.put('homestate', $scope.state);
					
					dataVer.put('prescriptionInfo', prescription);
					
					$state.go('prescription');
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
		}
		
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
		
		$scope.$watch('$viewContentLoaded', function() {

			// 页面加载完执行，刷新患者列表
			$scope.getPatientList();
			
			//初始化tab，以应对history.back回到之前的页面上
			var tabId = $scope.state.tabId;
			$('[id='+tabId+']').addClass('weui-bar__item_on').siblings().removeClass('weui-bar__item_on');
			$('.weui-tab__panel').find(tabId).show().siblings().hide();	  

			if(tabId == '#tab1'){
				$scope.title = "资讯";
				$scope.rightIcon = null;
				$scope.header_right_function = null;
			}
			if(tabId == '#tab2'){
				$scope.title = "我的处方";
				$scope.rightIcon = "./img/scan.png";
				$scope.header_right_function = $scope.scan;
			}
			if(tabId == '#tab3'){
				$scope.title = "患者";
				$scope.rightIcon = "./img/add2.png";
				$scope.header_right_function = $scope.addPatient;
			}
			if(tabId == '#tab4'){
				$scope.title = "我";
				$scope.rightIcon = null;
				$scope.header_right_function = null;
			}
			
		});

		console.log($window.wx);
    }];

});
