'use strict';

define([ 'angular' ], function(angular) {

	var module = angular.module('fluffyCloud.ec2.directives', []);

	module.directive('ngLoading', [ '$compile', function($compile) {
		return {
			restrict : 'A',
			scope : false,
			link : function(scope, element, attrs) {
				var initialContents = element.html();
				scope.$watch(attrs.ngLoading, function(loading) {
					if (loading == true) {
						element.html('<div class="loader"></div>');
					} else {
						element.html(initialContents);
						$compile(element.contents())(scope);
					}
				})
			}
		}

	} ]);

	return module;
});