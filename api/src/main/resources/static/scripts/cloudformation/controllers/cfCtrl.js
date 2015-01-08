define([], function() {
	'use strict';
	return [ '$scope', '$rootScope', 'toaster', 'CFSERVICE', function($scope, $rootScope, toaster, CFSERVICE) {
		$scope.describeStacks = function() {
			var scope = this;
			scope.jsonContent = 'Loading';
			CFSERVICE.describeStacks().success(function(data, status) {
				console.log(data);
				$scope.stacks = data.Stacks;
				scope.jsonContent = data.Stacks;
			}).error(function(data, status) {
				toaster.pop('error', 'Error while getting stacks');
				console.log(data, status);
			});
		}

		$scope.describeStack = function(stack) {
			var scope = this;
			scope.jsonContent = 'Loading';
			CFSERVICE.describeStacks(getPayload(stack.StackName)).success(function(data, status) {
				stack = data.Stacks[0];
				scope.jsonContent = stack;
			}).error(function(data, status) {
				toaster.pop('error', 'Error while getting stack');
				scope.jsonContent = 'Error while getting stack. Please try again';
				console.log(data, status);
			});
		}

		$scope.describeStackEvents = function(stack) {
			var scope = this;
			scope.jsonContent = 'Loading';

			CFSERVICE.describeStackEvents(getPayload(stack.StackName)).success(function(data, status) {
				console.log(data);
				scope.jsonContent = data;

			}).error(function(data, status) {
				toaster.pop('error', 'Error while getting stack events');
				scope.jsonContent = 'Error while getting stack events. Please try again';
				console.log(data, status);
			});
		}

		$scope.describeStackResources = function(stack) {
			var scope = this;
			scope.jsonContent = 'Loading';
			CFSERVICE.describeStackResources(getPayload(stack.StackName)).success(function(data, status) {
				console.log(data);
				scope.resources = data.StackResources;
				scope.jsonContent = data;
			}).error(function(data, status) {
				toaster.pop('error', 'Error while getting stack resources');
				scope.jsonContent = 'Error while getting stack resources. Please try again';
				console.log(data, status);
			});
		}

		$scope.describeStackResource = function(stack, logicalResourceId) {
			var scope = this.$parent;
			scope.jsonContent = 'Loading';
			var payLoad = {
				stackName : stack.StackName,
				logicalResourceId : logicalResourceId
			};
			CFSERVICE.describeStackResource(payLoad).success(function(data, status) {
				console.log(data);
				scope.jsonContent = data;
			}).error(function(data, status) {
				toaster.pop('error', 'Error while getting stack resources');
				scope.jsonContent = 'Error while getting stack resources. Please try again';
				console.log(data, status);
			});
		}

		$scope.listStackResources = function(stack) {
			var scope = this;
			scope.jsonContent = 'Loading';
			var payLoad = {
				stackName : stack.StackName,
			};
			CFSERVICE.listStackResources(payLoad).success(function(data, status) {
				console.log(data);
				scope.jsonContent = data;
			}).error(function(data, status) {
				toaster.pop('error', 'Error while listing stack resources');
				scope.jsonContent = 'Error while listing stack resources. Please try again';
				console.log(data, status);
			});
		}

		function getPayload(stackName) {
			return {
				stackName : stackName
			};

		}

		var init = function() {
			console.log($scope);
			$scope.describeStacks();
		}

		init();
	} ];
});