define(
		[],
		function() {
			'use strict';
			return [
					'$scope',
					'$rootScope',
					'EC2SERVICE',
					'toaster',
					'$modal',
					function($scope, $rootScope, EC2SERVICE, toaster, $modal) {

						$scope.isInstancesLoading = false;

						$scope.describeInstances = function(vpcId) {
							$rootScope.vpcId = vpcId;
							$scope.instances = null;

							var vpcFilter = [ {
								name : "vpc-id",
								values : [ vpcId ]
							} ];
							$scope.isInstancesLoading = true;
							EC2SERVICE
									.describeInstances(vpcFilter)
									.success(
											function(data, status) {
												$scope.isInstancesLoading = false;
												console.log(data, status);
												var reservations = data.Reservations[0];
												if (!angular
														.isUndefined(reservations)) {
													$scope.instances = reservations.Instances;
												} else {
													toaster
															.pop('warning',
																	'No Instance Found');
												}
											})
									.error(
											function(data, status) {
												toaster
														.pop('error',
																'Try Again',
																'Error while getting VPCs.');
												$scope.isInstancesLoading = false;
												console.log(data, status);
											});
						}

						$scope.describeVpcs = function() {
							$scope.isVpcsLoading = true;
							EC2SERVICE
									.describeVpcs()
									.success(function(data, status) {
										$scope.isVpcsLoading = false;
										$scope.vpcs = data.Vpcs;
									})
									.error(
											function(data, status) {
												toaster
														.pop('error',
																'Error while getting VPC details');
												$scope.isVpcsLoading = false;
												console.log(data, status);
											});
						}

						$scope.openCreateVPCForm = function() {
							var createVPCModal = $modal.open({
								templateUrl : 'views/createform.html',
								size : 'lg'

							})

						}

						var init = function() {
							$scope.describeVpcs();
						}

						init();
					} ];
		});