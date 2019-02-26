/**
 * angular-pull-to-refresh
 * @version v0.3.0 - 2013-11-14
 * @link https://github.com/mgcrea/angular-pull-to-refresh
 * @author Olivier Louvignes <olivier@mg-crea.com>
 * @license MIT License, http://www.opensource.org/licenses/MIT
 */
(function (window, document, undefined) {
  'use strict';
  angular.module('mgcrea.pullToRefresh', []).constant('pullToRefreshConfig', {
    treshold: 60,
    debounce: 400,
    text: {
      pull: '下拉刷新',
      release: '释放开始刷新',
      loading: '刷新中...'
    },
    icon: {
      pull: 'fa fa-arrow-down',
      release: 'fa fa-arrow-up',
      loading: 'fa fa-refresh fa-spin'
    }
  }).directive('pullToRefresh', [
    '$compile',
    '$timeout',
    '$q',
    'pullToRefreshConfig',
    function ($compile, $timeout, $q, pullToRefreshConfig) {
      return {
        scope: true,
        restrict: 'A',
        transclude: true,
        templateUrl: 'angular-pull-to-refresh.tpl.html',
        compile: function compile(tElement, tAttrs, transclude) {
          return function postLink(scope, iElement, iAttrs) {
            var config = angular.extend({}, pullToRefreshConfig, iAttrs);
            var scrollElement = iElement.parent(); //获取滚动的元素
            var ptrElement = window.ptr = iElement.children()[0];
            scope.text = config.text;
            scope.icon = config.icon;
            scope.status = 'pull';
			scope._start = 0;
			scope._end = 0;
            var setStatus = function (status) {
              shouldReload = status === 'release';
              scope.$apply(function () {
                scope.status = status;
              });
            };
            var shouldReload = false;
			scrollElement.bind('touchstart',function(ev){
				var touch = event.targetTouches[0];						
				scope._start = touch.pageY;
				//console.log('ystart' + scope._start);
			});
            scrollElement.bind('touchmove', function (ev) {
				var touch = event.targetTouches[0];
				scope._end = (scope._start - touch.pageY);
				//下滑才执行操作
				if (scope._end < 0) {
					//console.log('scope._end==='+scope._end);
					var moveMargin = 0;
					if (scrollElement[0].scrollTop <= 0) {//保障一定是顶部往下拉
						if (scope._end <= -80) {
							moveMargin = -80;
						}else{
							moveMargin = scope._end;
						}
						
						ptrElement.style.margin = (-40-moveMargin)+" auto 0 auto";//松开刷新的高度
					
						if(scope._end <= -config.treshold){// && !shouldReload){					
							setStatus('release');
						}else{
							setStatus('pull');
						}						
					} else {
						return; 
					}
				}
				/*
              var top = scrollElement[0].scrollTop;
              if (top < -config.treshold && !shouldReload) {
                setStatus('release');
              } else if (top > -config.treshold && shouldReload) {
                setStatus('pull');
              }
			  */
            });
            scrollElement.bind('touchend', function (ev) {
              if (!shouldReload){
                ptrElement.style.margin = '-40 auto 0 auto';//若不刷新则收起
				return;
			  }
              ptrElement.style.webkitTransitionDuration = 0;
              ptrElement.style.margin = '0 auto';
              setStatus('loading');
              var start = +new Date();
              $q.when(scope.$eval(iAttrs.pullToRefresh)).then(function () {
                var elapsed = +new Date() - start;
                $timeout(function () {
                  ptrElement.style.margin = '-40 auto 0 auto';
                  ptrElement.style.webkitTransitionDuration = '';
                  scope.status = 'pull';
                }, elapsed < config.debounce ? config.debounce - elapsed : 0);
              });
            });
            scope.$on('$destroy', function () {
			  scrollElement.unbind('touchstart');
              scrollElement.unbind('touchmove');
              scrollElement.unbind('touchend');
            });
          };
        }
      };
    }
  ]);
  angular.module('mgcrea.pullToRefresh').run([
    '$templateCache',
    function ($templateCache) {
      $templateCache.put('angular-pull-to-refresh.tpl.html', '<div class=\'pull-to-refresh\'>\n' + '  <i ng-class=\'icon[status]\'></i>&nbsp;\n' + '  <span ng-bind=\'text[status]\'></span>\n' + '</div>\n' + '<div ng-transclude></div>\n');
    }
  ]);
}(window, document));