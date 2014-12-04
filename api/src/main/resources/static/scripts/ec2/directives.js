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

	module.directive('loader', function() {
		return {
			restrict : 'AE',
			replace : 'true',
			transclude : 'true',
			templateUrl : 'views/loader.html'
		};
	});

	module.directive('slideToggle',
			function() {
				return {
					restrict : 'A',
					scope : {
						isOpen : "=slideToggle" // 'data-slide-toggle' in our
												// html
					},
					link : function(scope, element, attr) {
						var slideDuration = parseInt(attr.slideToggleDuration,
								10) || 200;

						// Watch for when the value bound to isOpen changes
						// When it changes trigger a slideToggle
						scope.$watch('isOpen', function(newIsOpenVal,
								oldIsOpenVal) {
							if (newIsOpenVal !== oldIsOpenVal) {
								element.stop().slideToggle(slideDuration);
							}
						});

					}
				};
			});

	return module;
});