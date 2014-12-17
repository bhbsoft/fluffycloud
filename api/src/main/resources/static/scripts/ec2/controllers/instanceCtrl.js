define([], function() {
	'use strict';
	return [ '$scope', '$rootScope', 'EC2SERVICE', 'toaster', '$state', 'MODALSERVICE',
			function($scope, $rootScope, EC2SERVICE, toaster, $state, MODALSERVICE) {

				$scope.creatingInstance = false;
				$scope.createInstanceRequest = {
					createVpcRequest : {
						tags : {}
					},
					createSubnetRequest : {
						tags : {}
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

				$scope.startInstances = function(instance) {
					var payLoad = {
						accessKey : "accessKey",
						ids : [ instance.InstanceId ]
					}

					instance.State.Name = 'transition';

					EC2SERVICE.startInstances(payLoad).success(function(data, status) {
						console.log(data);
						instance.State.Name = 'running';
						toaster.pop('success', 'Instance Started.');
					}).error(function(data, status) {
						toaster.pop('error', 'Try Again', 'Error while starting instance.');
					});
				}

				$scope.stopInstances = function(instance) {
					var payLoad = {
						accessKey : "accessKey",
						ids : [ instance.InstanceId ]
					}

					instance.State.Name = 'transition';
					EC2SERVICE.stopInstances(payLoad).success(function(data, status) {
						console.log(data);
						instance.State.Name = 'stopped';
						toaster.pop('success', 'Instance Stopped.');
					}).error(function(data, status) {
						toaster.pop('error', 'Try Again', 'Error while stopping instance.');

					});
				}

				$scope.setActiveInstance = function(instance) {
					$scope.RouteTables = {};
					$scope.SecurityGroups = {};
					EC2SERVICE.clearActiveContext();
					EC2SERVICE.setActiveInstance(instance);
					$scope.describeSG();
					$state.go('home.vpc.summary.securitygroup');
				}

				$scope.describeSG = function() {
					$scope.isSGLoading = true;
					var payLoad = {
						accessKey : "accessKey",
						ids : []
					};
					var instance = EC2SERVICE.getActiveInstance();
					angular.forEach(instance.SecurityGroups, function(value, key) {
						payLoad.ids.push(value.GroupId);
					});
					EC2SERVICE.describeSecurityGroup(payLoad).success(function(data, status) {
						$scope.isSGLoading = false;
						console.log(data);
						$scope.SecurityGroups = data.SecurityGroups;
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

					EC2SERVICE.describeRouteTables(payLoad).success(function(data, status) {
						$scope.isRTLoading = false;
						console.log(data);
						$scope.RouteTables = data.RouteTables;
					}).error(function(data, status) {
						$scope.isRTLoading = false;
						console.log(data);
						toaster.pop('error', 'Try Again');
					});
				}

				$scope.describeSubnets = function(vpcId) {
					$scope.isSubnetLoading = true;
					var vpcId = (vpcId == null ? $rootScope.vpcId : vpcId);
					var payLoad = {
						Filter : [ {
							name : "vpc-id",
							values : [ vpcId ]
						} ]
					};
					EC2SERVICE.describeSubnets(payLoad).success(function(data, status) {
						$scope.isSubnetLoading = false;
						console.log(data);
						$scope.subnets = data.Subnets;

					}).error(function(data, status) {
						$scope.isSubnetLoading = false;
						console.log(data);
						toaster.pop('error', 'Try Again');

					});
				}

				$scope.createInstance = function() {
					$scope.creatingInstance = true;
					EC2SERVICE.createInstance($scope.createInstanceRequest).success(function(data, status) {
						// TODO clear form and move to homepage
						$scope.creatingInstance = false;
						toaster.pop('success', 'Instance created');

					}).error(function(data, status) {
						$scope.creatingInstance = false;
						toaster.pop('error', 'Try Again');

					});
				}

				$scope.getVPCList = function() {
					$scope.isVPCLoading = true;
					EC2SERVICE.describeVpcs().success(function(data, status) {
						$scope.isVPCLoading = false;
						$scope.vpcs = data.Vpcs;
						$scope.createInstanceRequest.createVpcRequest.vpcId = $scope.vpcs[0].VpcId;
					}).error(function(data, status) {
						$scope.isVPCLoading = false;
						toaster.pop('error', 'Error while getting VPC details');
					});
				}

				$scope.getSGsForVPC = function(vpcId) {
					$scope.isSGLoading = true;
					var payLoad = {
						Filter : [ {
							name : "vpc-id",
							values : [ vpcId ]
						} ]
					};
					EC2SERVICE.describeSecurityGroup(payLoad).success(function(data, status) {
						$scope.isSGLoading = false;
						$scope.vpcSGs = data.SecurityGroups;
					}).error(function(data, status) {
						$Scope.isSGLoading = false;
						toaster.pop('error', 'Error while geting security group.', ' Please reload.');
					});
				}

				$scope.vpcSelected = function(vpcId) {
					if (null != vpcId) {
						$scope.describeSubnets(vpcId);
						$scope.getSGsForVPC(vpcId);
					}
				}

				$scope.describeKeyPair = function() {
					$scope.isKeyPairLoading = true;
					EC2SERVICE.describeKeyPairs().success(function(data, status) {
						console.log(data);
						$scope.isKeyPairLoading = false;
						$scope.keyPairs = data.KeyPairs;
					}).error(function(data, status) {
						$scope.isKeyPairLoading = false;
						toaster.pop('error', 'Error while getting key pairs.', ' Please reload.');
					});
				}

				$scope.createNewVpc = function() {
					$scope.vpcs = {};
					$scope.subnets = {};
					$scope.vpcSGs = {};
					$scope.createInstanceRequest.createSecurityGroupRequest.securityGroupId = null;
					$scope.createInstanceRequest.createSubnetRequest.subnetId = null;
					$scope.createInstanceRequest.createVpcRequest.vpcId = null;
					$scope.showVPCForm = !$scope.showVPCForm;

					if (!$scope.showVPCForm) {
						$scope.getVPCList();
					}
				}

				var init = function() {
					$scope.getVPCList();
					$scope.describeKeyPair();

				}

				init();
			} ]
});