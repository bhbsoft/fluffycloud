define([], function() {
	'use strict';
	return [ '$scope', '$rootScope', 'EC2SERVICE',
			function($scope, $rootScope, EC2SERVICE) {
				$scope.describeVpcs = function() {
					EC2SERVICE.describeVpcs().success(function(data, status) {
						$scope.vpcs = data.Vpcs;
					}).error(function(data, status) {
						console.log(data, status);
					});
				}

				$scope.describeInstances = function(vpcId) {
					var vpcFilter = [ {
						name : "vpc-id",
						values : [ vpcId ]
					} ];
					EC2SERVICE.describeInstances(vpcFilter).success(function(data, status) {
						console.log(data, status);
					}).error(function(data, status) {
						console.log(data, status);
					});
				}

				var init = function() {
					$scope.describeVpcs();

				}

				init();
			} ];
});