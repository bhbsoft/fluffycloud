'use strict';

define([ 'angular' ], function(angular) {

	var module = angular.module('fluffyCloud.cf.directives', []);

	module.directive('ngfile', [ '$parse', function($parse) {
		return {
			restrict : 'A',
			link : function(scope, element, attrs) {
				var ngFileModel = $parse(attrs.ngfile);
				var modelSetter = ngFileModel.assign;

				element.bind('change', function() {
					scope.$apply(function() {
						modelSetter(scope, element[0].files[0]);
					});
				});
			}
		}
	} ]);

	return module;
});