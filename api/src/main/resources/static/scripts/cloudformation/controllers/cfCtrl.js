define([], function() {
	'use strict';
	return [ '$scope', '$rootScope', 'toaster', 'CFSERVICE', function($scope, $rootScope, toaster, CFSERVICE) {
		$scope.describeStacks = function() {
			$scope.stacks = null;
			CFSERVICE.describeStacks().success(function(data, status) {
				console.log(data);
				$scope.stacks = data.Stacks;
				$scope.jsonContent = data.Stacks;
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
				toaster.pop('error', 'Error while getting stack resource');
				scope.jsonContent = 'Error while getting stack resource. Please try again';
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

		$scope.deleteStack = function(index) {
			var scope = this;
			scope.jsonContent = 'Deleting';
			var payLoad = {
				stackName : $scope.stacks[index].StackName,
			};
			CFSERVICE.deleteStack(payLoad).success(function(data, status) {
				console.log(data);
				scope.jsonContent = data;
			}).error(function(data, status) {
				toaster.pop('error', 'Error while deleting stack.');
				scope.jsonContent = 'Error while deleting stack. Please try again';
				console.log(data, status);
			});
		}

		$scope.listStacks = function() {
			var scope = this;
			scope.jsonContent = 'Loading';
			CFSERVICE.listStacks().success(function(data, status) {
				console.log(data);
				scope.jsonContent = data;
			}).error(function(data, status) {
				toaster.pop('error', 'Error while listing stacks.');
				scope.jsonContent = 'Error while listing stacks. Please try again';

			});
		}

		$scope.getStackTemplates = function() {
			CFSERVICE.getStackTemplates().success(function(data, status) {
				console.log(data);
				$scope.templates = data;
			}).error(function(data, status) {
				toaster.pop('error', 'Error while getting templates.');
			});
		}

		$scope.updateTemplateForm = function(templateName, templateJson) {
			$scope.createStackRequest = {
				templateParams : {}
			};
			$scope.createStackRequest.templateName = templateName;
			$scope.selectedTemplate = angular.fromJson(templateJson);
			$scope.displayForm = true;
		}

		$scope.createStack = function() {
			console.log($scope.createStackRequest);
			CFSERVICE.createStack($scope.createStackRequest).success(function(data, status) {
				console.log(data);
				$scope.createStackRequest = {
					templateParams : {}
				};
				$scope.describeStacks();
				toaster.pop('success', 'Stack Created!');
			}).error(function(data, status) {
				toaster.pop('error', 'Error while getting templates.');
			});
		}

		$scope.addTemplateRequest = {
			templateName : null,
			validateOnly : false
		};

		$scope.addTemplate = function() {
			var payLoad = new FormData();
			payLoad.append("templateFile", $scope.addTemplateRequest.templateFile);
			payLoad.append("templateJson", $scope.addTemplateRequest.templateJson);
			if (!$scope.addTemplateRequest.validateOnly) {
				payLoad.append("templateName", $scope.addTemplateRequest.templateName);
			}
			payLoad.append("validateOnly", $scope.addTemplateRequest.validateOnly);

			CFSERVICE.addTemplate(payLoad).success(function(data, status) {
				console.log(data);
				if ($scope.addTemplateRequest.validateOnly) {
					toaster.pop('success', 'Valid Template');
				} else {
					toaster.pop('success', 'Template validated and added.');
				}
			}).error(function(data, status) {
				toaster.pop('error', 'Error while validating template.');
			});
		}

		$scope.updateStack = function(stack) {
			CFSERVICE.updateStack(this.updateStackRequest).success(function(data, status) {
				console.log(data);
				toaster.pop('success', 'Template updated.');
			}).error(function(data, status) {
				toaster.pop('error', 'Error while updating template.');
			});
		}

		function getPayload(stackName) {
			return {
				stackName : stackName
			};
		}

		var init = function() {
			$scope.describeStacks();
		}

		init();
	} ];
});