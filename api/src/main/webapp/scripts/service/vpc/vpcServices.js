define([], function() {
	'use strict';
	return [ '$scope', '$rootScope', function($scope, $rootScope) {

		var jsonConfig = {
			headers : {
				'Content-Type' : 'application/json'
			}
		};

		this.describeVpcs = function() {
			return $http.get(Constants.baseUrl + '/ec2/describevpcs');
		}

	} ];
});