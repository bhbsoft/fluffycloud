define([], function() {
	'use strict';
	return [ '$scope', '$rootScope', 'toaster', 'CFSERVICE', function($scope, $rootScope, toaster, CFSERVICE) {
		$scope.describeStacks = function() {
			CFSERVICE.describeStacks().success(function(data, status) {
				console.log(data);
				$scope.stacks = data.Stacks;
			}).error(function(data, status) {
				toaster.pop('error', 'Error while getting stacks');
				console.log(data, status);
			});
		}

		$scope.describeStack = function(stack) {
			var payLoad = {
				stackName : stack.stackName
			};
			CFSERVICE.describeStacks(payLoad).success(function(data, status) {
				stack = data.Stacks[0];
			}).error(function(data, status) {
				toaster.pop('error', 'Error while getting stack');
				console.log(data, status);
			});
		}

		var init = function() {
			$scope.describeStacks();
		}

		init();
	} ];
});