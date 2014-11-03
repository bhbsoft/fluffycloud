define([ 'angular' ], function(angular) {
	'use strict';
	return [
			'$http',
			'Constants',
			function($http, Constants) {

				var jsonConfig = {
					headers : {
						'Content-Type' : 'application/json'
					}
				};

				this.describeVpcs = function() {
					return $http.get(Constants.baseUrl + '/ec2/describevpcs',
							jsonConfig);
				}

				this.describeInstances = function(payLoad) {
					return $http.get(Constants.baseUrl
							+ '/ec2/describeinstances', {
						params : {
							filter : JSON.stringify(payLoad)
						}
					});
				}

			} ];
});