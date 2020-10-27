
	angular.module('myApp',[])

	  .controller('AppCtrl', function($scope) {

		 console.log('11111');
	  });
   
    angular.module('myApp').directive('reloadData', function () {
		return {
			restrict: 'A',
			template: "<div id='pullDown'>	<span class='pullDownIcon'></span><span class='pullDownLabel'>加载中...</span></div>",
			link: function ($scope) {
				var scrollerId = "scroller";
				var pullDownId = "pullDown";
				// var scrollerHeight = "130"
				//   document.getElementById(scrollerId)
				//scrollerE = document.getElementById(scrollerId);
				//scrollerE.addEventListener("scroll", function () {
					//$scope.scrollTop = $("#" + scrollerId).scrollTop()
					//console.log($scope.scrollTop);
					//if ($scope.scrollTop <= 0) {
					    //console.log('刷新启动');
						//k_touch("y");
					//} else {
						//console.log('未激活刷新');
					//}
				//});
				//scrollerE.scrollTop = 40;//让元素自动上移
				//console.log('init=='+scrollerE.scrollTop);
				/////////////////////////////////////////
				function slideDownStep1(dist) { // dist 下滑的距离，用以拉长背景模拟拉伸效果
					$scope.pullDown = document.getElementById(pullDownId);
					$scope.pullDown.className = '';
					var pullshowHeight = 40 - dist;
					if (pullshowHeight > 100) {
						pullshowHeight = 100;
					}
					$scope.pullDown.style.height = pullshowHeight + "px";//松开刷新的高度
					$scope.pullDown.querySelector('.pullDownLabel').innerHTML = '松手开始更新...';
					$scope.pullDown.style.display = "block";//松开刷新none
				}
				function slideDownStep2() {
					$scope.pullDown.className = 'loading';
					$scope.pullDown.querySelector('.pullDownLabel').innerHTML = '加载中...';
					$scope.pullDown.style.height = "40px";//高度设定为20px 
					//刷新数据
					//location.reload();//加载数据
				}
				function slideDownStep3() {
					$scope.pullDown.style.display = "none";//松开刷新none 
				}
				////////////////////////
				//function k_touch(way) {

					$scope._start = 0;
					$scope._end = 0;
					document.getElementById(scrollerId).addEventListener("touchstart", touchStart, false);//当手指触摸屏幕时候触发，即使已经有一个手指放在屏幕上也会触发。
					document.getElementById(scrollerId).addEventListener("touchmove", touchMove, false);//当手指在屏幕上滑动的时候连续地触发。在这个事件发生期间，调用preventDefault()事件可以阻止滚动。
					document.getElementById(scrollerId).addEventListener("touchend", touchEnd, false);
					////////////////////////////////////////////////
					function touchStart(event) {//touchStart函数
						//console.log('touchStart=='+$scope.scrollerE.scrollTop);
						var touch = event.targetTouches[0];
						//if (way == "x") {
							//$scope._start = touch.pageX;
							//console.log('x' + $scope._start);
						//} else {
							$scope._start = touch.pageY;
							console.log('ystart' + $scope._start);

						//}
					}
					//////////////////////////////////////////////
					function touchMove(event) {//touchMove函数
						//console.log('touchMove=='+$scope.scrollerE.scrollTop);
						var touch = event.targetTouches[0];
						//if (way == "x") {
							//$scope._end = ($scope._start - touch.pageX);
						//} else {
							$scope._end = ($scope._start - touch.pageY);
							//下滑才执行操作
							if ($scope._end < 0) {
								console.log('$scope._end==='+$scope._end);
								if ($("#" + scrollerId).scrollTop() <= 0) {
									slideDownStep1($scope._end); 
								} else {
									return; 
								}
							}
						//}

					}
					/////////////////////////////////////////////
					function touchEnd(event) {//touchEnd函数
						//console.log('touchEnd=='+$scope.scrollerE.scrollTop);
						if ($scope._end >= 0) {
							// console.log("左滑或上滑 " + $scope._end);
						} else {
							console.log("右滑或下滑" + $scope._end);
							//////////////// 
								////////////////
								slideDownStep2();
								//刷新成功则
								//模拟刷新成功进入第三步
								setTimeout(function () {
									slideDownStep3();
									$("#" + scrollerId).scrollTop(0)
								}, 3000);
								//////////////////
						  

						}

					}
					/////////////////////////////////////////////
				//}
			}
			///////////////////////
		}

	})
