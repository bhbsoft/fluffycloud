define([], function() {
	'use strict';
	return [ '$scope', '$rootScope', 'EC2SERVICE', 'toaster',
			function($scope, $rootScope, EC2SERVICE, toaster) {

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

			} ]
});