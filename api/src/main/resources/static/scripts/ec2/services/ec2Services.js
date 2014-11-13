define([ 'angular' ], function(angular) {
	'use strict';
	return [ '$http', 'Constants', function($http, Constants) {

		var jsonConfig = {
			headers : {
				'Content-Type' : 'application/json'
			}
		};

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
					filter : JSON.stringify(payLoad.Filter)
				}
			});
		}
		
		this.startInstances = function(payLoad) {
			return $http.post(Constants.baseUrl + '/startinstances',JSON.stringify(payLoad), jsonConfig);
		}
		
		this.stopInstances = function(payLoad) {
			return $http.post(Constants.baseUrl + '/stopinstances',JSON.stringify(payLoad), jsonConfig);
		}
		
		this.describeRouteTables = function(payLoad) {
			return $http.get(Constants.baseUrl + '/describeroutetables', {
				params : {
					filter : JSON.stringify(payLoad.Filter)
				}
			});
		}
		
		this.describeSubnets= function(payLoad) {
			return $http.get(Constants.baseUrl + '/describesubnets', {
				params : {
					filter : JSON.stringify(payLoad.Filter)
				}
			});
		}


	} ];
});