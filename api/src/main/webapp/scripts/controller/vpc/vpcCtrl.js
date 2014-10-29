define([], function() {
	'use strict';
	return [ '$scope', '$rootScope', 'VPCSERVICE', function($scope, $rootScope, VPCSERVICE) {

		$scope.describeVpcs = function() {
			var json = VPCSERVICE.describeVpcs();
			console.log(json);
		}

	} ];
});