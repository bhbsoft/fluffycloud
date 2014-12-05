define([], function() {
	'use strict';
	return [
			'$scope',
			'$rootScope',
			'EC2SERVICE',
			'toaster',
			'$state',
			'MODALSERVICE',
			function($scope, $rootScope, EC2SERVICE, toaster, $state,
					MODALSERVICE) {

				$scope.createInstanceRequest = {
					createVpcRequest : {
						tags : {}
					},
					createSubnetRequest : {
						"tags" : {}
					},
					createSecurityGroupRequest : {
						tags : {}
					},
					tags : {}
				}

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

					$scope.isSGLoading = true;

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
								$scope.isSGLoading = false;
								console.log(data);
								$scope.SecurityGroups = data.SecurityGroups;
								toaster.pop('success', 'Got SG');
							}).error(function(data, status) {
						$scope.isSGLoading = false;
						console.log(data);
						toaster.pop('error', 'Try Again');

					});
				}
				$scope.describeRouteTables = function() {
					$scope.isRTLoading = true;
					var payLoad = {
						Filter : [ {
							name : "vpc-id",
							values : [ $rootScope.vpcId ]
						} ]
					};

					EC2SERVICE.describeRouteTables(payLoad).success(
							function(data, status) {
								$scope.isRTLoading = false;
								console.log(data);
								$scope.RouteTables = data.RouteTables;
							}).error(function(data, status) {
						$scope.isRTLoading = false;
						console.log(data);

						toaster.pop('error', 'Try Again');

					});
				}

				$scope.describeSubnets = function(selectedVpc) {
					$scope.isSubnetLoading = true;
					var vpcId = (selectedVpc == null ? $rootScope.vpcId
							: selectedVpc.VpcId);
					var payLoad = {
						Filter : [ {
							name : "vpc-id",
							values : [ vpcId ]
						} ]
					};

					EC2SERVICE.describeSubnets(payLoad).success(
							function(data, status) {
								$scope.isSubnetLoading = false;
								console.log(data);
								$scope.subnets = data.Subnets;

							}).error(function(data, status) {
						$scope.isSubnetLoading = true;
						console.log(data);
						toaster.pop('error', 'Try Again');

					});
				}

				$scope.openCreateInstanceForm = function() {
					$state.go('home.instance');
				}

				$scope.createInstance = function() {
					console.log($scope.createInstanceRequest);
					EC2SERVICE.createInstance($scope.createInstanceRequest)
							.success(function(data, status) {
								// TODO clear form and move to homepage
								toaster.pop('success', 'Instance created');

							}).error(function(data, status) {

								toaster.pop('error', 'Try Again');

							});
				}

				$scope.getVPCList = function() {
					EC2SERVICE.describeVpcs().success(function(data, status) {
						$scope.vpcs = data.Vpcs;
					}).error(
							function(data, status) {
								toaster.pop('error',
										'Error while getting VPC details');

							});
				}

				var init = function() {
					$scope.getVPCList();
				}

				init();
			} ]
});