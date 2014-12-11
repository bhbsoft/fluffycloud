define([ 'angular' ], function(angular) {
	'use strict';
	return [ '$http', 'Constants', function($http, Constants) {

		var jsonConfig = {
			headers : {
				'Content-Type' : 'application/json'
			}
		};

		var activeInstance;

		this.clearActiveContext = function(instance) {
			activeInstance = {};
		}

		this.setActiveInstance = function(instance) {
			activeInstance = instance;
		}

		this.getActiveInstance = function() {
			return activeInstance;
		}

		this.describeVpcs = function() {
			return $http.get(Constants.baseUrl + '/describevpcs', jsonConfig);
		}

		this.describeInstances = function(payLoad) {
			return $http.get(Constants.baseUrl + '/describeinstances', {
				params : {
					filter : JSON.stringify(payLoad)
				}
			});
		}

		this.describeSecurityGroup = function(payLoad) {
			return $http.get(Constants.baseUrl + '/describesg', {
				params : {
					ids : payLoad.ids,
					filter : JSON.stringify(payLoad.Filter)
				}
			});
		}

		this.startInstances = function(payLoad) {
			return $http.post(Constants.baseUrl + '/startinstances', JSON.stringify(payLoad), jsonConfig);
		}

		this.stopInstances = function(payLoad) {
			return $http.post(Constants.baseUrl + '/stopinstances', JSON.stringify(payLoad), jsonConfig);
		}

		this.createVPC = function(payLoad) {
			return $http.post(Constants.baseUrl + '/createvpc', JSON.stringify(payLoad), jsonConfig);
		}

		this.describeRouteTables = function(payLoad) {
			return $http.get(Constants.baseUrl + '/describeroutetables', {
				params : {
					filter : JSON.stringify(payLoad.Filter)
				}
			});
		}

		this.describeSubnets = function(payLoad) {
			return $http.get(Constants.baseUrl + '/describesubnets', {
				params : {
					filter : JSON.stringify(payLoad.Filter)
				}
			});
		}

		this.createInstance = function(payLoad) {
			return $http.post(Constants.baseUrl + '/createinstance', JSON.stringify(payLoad), jsonConfig);
		}

		this.describeKeyPairs = function() {
			return $http.get(Constants.baseUrl + '/describekeypairs', jsonConfig);
		}

	} ];
});