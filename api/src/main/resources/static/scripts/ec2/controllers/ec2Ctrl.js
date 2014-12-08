define([], function() {
	'use strict';
	return [ '$scope', '$rootScope', 'EC2SERVICE', 'toaster', '$modal', 'MODALSERVICE',
			function($scope, $rootScope, EC2SERVICE, toaster, $modal, MODALSERVICE) {

				$scope.isInstancesLoading = false;
				$scope.vpc = {};

				$scope.describeInstances = function(vpcId) {
					$rootScope.vpcId = vpcId;
					$scope.instances = null;

					var vpcFilter = [ {
						name : "vpc-id",
						values : [ vpcId ]
					} ];
					$scope.isInstancesLoading = true;
					EC2SERVICE.describeInstances(vpcFilter).success(function(data, status) {
						$scope.instances = [];
						$scope.isInstancesLoading = false;

						angular.forEach(data.Reservations, function(value, key) {
							if (!angular.isUndefined(value)) {
								$scope.instances.push(value.Instances[0]);
							}
						});
						console.log($scope.instances);
					}).error(function(data, status) {
						toaster.pop('error', 'Try Again', 'Error while getting VPCs.');
						$scope.isInstancesLoading = false;
						console.log(data, status);
					});
				}

				$scope.describeVpcs = function() {
					$scope.isVpcsLoading = true;
					EC2SERVICE.describeVpcs().success(function(data, status) {
						$scope.isVpcsLoading = false;
						$scope.vpcs = data.Vpcs;
					}).error(function(data, status) {
						toaster.pop('error', 'Error while getting VPC details');
						$scope.isVpcsLoading = false;
						console.log(data, status);
					});
				}

				$scope.createVPC = function() {
					$scope.vpcCreated = false;

					var payLoad = {
						cidrBlock : $scope.vpc.cidr,
						instanceTenancy : $scope.vpc.tenancy,
						tags : {
							"Name" : $scope.vpc.name
						}
					}

					EC2SERVICE.createVPC(payLoad).success(function(data, status) {
						$scope.vpcCreated = true;
						// add to VPC list
						console.log(data);
						$scope.vpcs.push(data.Vpc);
						toaster.pop('success', 'Created VPC.');
						$scope.vpcModal.dismiss('cancel');
					}).error(function(data, status) {
						toaster.pop('error', 'Error while createing VPC.');
						$scope.isVpcsLoading = false;
						console.log(data, status);
						$scope.vpcModal.dismiss('cancel');
					});
				}

				$scope.openCreateVPCForm = function() {
					$scope.vpcModal = MODALSERVICE.showModal('views/modals/createVPCform.html', $scope, 'lg');
				}

				var init = function() {
					$scope.describeVpcs();
				}

				init();
			} ];
});