define([], function() {
	'use strict';
	return [ '$scope', '$rootScope', 'EC2SERVICE', 'toaster', function($scope, $rootScope, EC2SERVICE, toaster) {

		$scope.isInstancesLoading = true;
		$scope.instances = null;

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

		$scope.describeInstances = function(vpcId) {
			$scope.vpcId = vpcId;
			var vpcFilter = [ {
				name : "vpc-id",
				values : [ vpcId ]
			} ];
			EC2SERVICE.describeInstances(vpcFilter).success(function(data, status) {
				$scope.isInstancesLoading = false;
				console.log(data, status);
				var reservations = data.Reservations[0];
				if (!angular.isUndefined(reservations)) {
					$scope.instances = reservations.Instances;
				}
				else {
					toaster.pop('warning', 'No Instance Found');
				}
			}).error(function(data, status) {
				toaster.pop('error', 'Try Again', 'Error while getting VPCs.');
				$scope.isInstancesLoading = false;
				console.log(data, status);
			});
		}
		
		
		$scope.startInstances = function(instanceId) {	
			var payLoad = {
					accessKey:"accessKey",
					instanceIds: [instanceId]
			}
			EC2SERVICE.startInstances(payLoad).success(function(data, status) {
				console.log(data);	
				
				angular.forEach($scope.instances, function(value, key){
					if(angular.equals(value.InstanceId, instanceId))
					{
						value.State.Name ='running';
					}					
				});
				toaster.pop('success', 'Instance Started.');
			}).error(function(data, status) {
				toaster.pop('error', 'Try Again', 'Error while starting instance.');				
			});
		}
		
		$scope.stopInstances = function(instanceId) {	
			var payLoad = {
					accessKey:"accessKey",
					instanceIds: [instanceId]
			}
			EC2SERVICE.stopInstances(payLoad).success(function(data, status) {
				console.log(data);	
				angular.forEach($scope.instances, function(value, key){
					if(angular.equals(value.InstanceId, instanceId))
					{
						value.State.Name ='stopped';
					}					
				});
				toaster.pop('success', 'Instance Stopped.');
			}).error(function(data, status) {
				toaster.pop('error', 'Try Again', 'Error while stopping instance.');
				
			});
		}


		var init = function() {
			$scope.describeVpcs();
		}

		init();
	} ];
});