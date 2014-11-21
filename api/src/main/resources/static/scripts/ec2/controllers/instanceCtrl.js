define([], function() {
	'use strict';
	return [
			'$scope',
			'$rootScope',
			'EC2SERVICE',
			'toaster',
			'$state',
			function($scope, $rootScope, EC2SERVICE, toaster, $state) {

				$scope.tabs = [ {
					heading : "Security Groups",
					route : "home.vpc.summary.securitygroup",
					active : true
				}, {
					heading : "Route Tables",
					route : "home.vpc.summary.routetable",
					active : false
				}, {
					heading : "Subnets",
					route : "home.vpc.summary.subnet",
					active : false
				} ];

				$scope.go = function(route) {
					$state.go(route);
				}

				$scope.active = function(route) {
					return $state.is(route);
				}

				$scope.$on("$stateChangeSuccess", function() {
					$scope.tabs.forEach(function(tab) {
						tab.active = $scope.active(tab.route);
					});
				})

				$scope.startInstances = function(instanceId) {
					var payLoad = {
						accessKey : "accessKey",
						instanceIds : [ instanceId ]
					}
					EC2SERVICE.startInstances(payLoad).success(
							function(data, status) {
								console.log(data);

								angular.forEach($scope.instances, function(
										value, key) {
									if (angular.equals(value.InstanceId,
											instanceId)) {
										value.State.Name = 'running';
									}
								});
								toaster.pop('success', 'Instance Started.');
							}).error(
							function(data, status) {
								toaster.pop('error', 'Try Again',
										'Error while starting instance.');
							});
				}

				$scope.stopInstances = function(instanceId) {
					var payLoad = {
						accessKey : "accessKey",
						ids : [ instanceId ]
					}
					EC2SERVICE.stopInstances(payLoad).success(
							function(data, status) {
								console.log(data);
								angular.forEach($scope.instances, function(
										value, key) {
									if (angular.equals(value.InstanceId,
											instanceId)) {
										value.State.Name = 'stopped';
									}
								});
								toaster.pop('success', 'Instance Stopped.');
							}).error(
							function(data, status) {
								toaster.pop('error', 'Try Again',
										'Error while stopping instance.');

							});
				}

				$scope.setActiveInstance = function(instance) {
					$scope.RouteTables = {};
					$scope.SecurityGroups = {};
					EC2SERVICE.clearActiveContext();
					EC2SERVICE.setActiveInstance(instance);
				}

				$scope.describeSG = function() {
					var payLoad = {
						accessKey : "accessKey",
						ids : []
					};
					var instance = EC2SERVICE.getActiveInstance();

					angular.forEach(instance.SecurityGroups, function(value,
							key) {
						payLoad.ids.push(value.GroupId);
					});

					EC2SERVICE.describeSecurityGroup(payLoad).success(
							function(data, status) {
								console.log(data);
								$scope.SecurityGroups = data.SecurityGroups;
								toaster.pop('success', 'Got SG');
							}).error(function(data, status) {
						console.log(data);
						toaster.pop('error', 'Try Again');

					});
				}
				$scope.describeRouteTables = function() {
					var payLoad = {
						Filter : [ {
							name : "vpc-id",
							values : [ $rootScope.vpcId ]
						} ]
					};

					EC2SERVICE.describeRouteTables(payLoad).success(
							function(data, status) {
								console.log(data);
								$scope.RouteTables = data.RouteTables;
							}).error(function(data, status) {
						console.log(data);
						toaster.pop('error', 'Try Again');

					});
				}

				$scope.describeSubnets = function() {
					var payLoad = {
						Filter : [ {
							name : "vpc-id",
							values : [ $rootScope.vpcId ]
						} ]
					};

					EC2SERVICE.describeSubnets(payLoad).success(
							function(data, status) {
								console.log(data);
								$scope.subnets = data.Subnets;

							}).error(function(data, status) {
						console.log(data);
						toaster.pop('error', 'Try Again');

					});
				}

			} ]
});