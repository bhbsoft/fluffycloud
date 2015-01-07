define([ 'angular' ], function(angular) {
	'use strict';
	return [ '$http', 'CFServiceConstants', function($http, Constants) {
		var jsonConfig = {
			headers : {
				'Content-Type' : 'application/json'
			}
		};

		this.describeStacks = function(payLoad) {
			var stackName = angular.isUndefined(payLoad) ? null : payLoad.stackName;
			return $http.get(Constants.baseUrl + '/describestacks', {
				params : {
					stackName : stackName,
				}
			});
		}

		this.listStacks = function(payLoad) {
			return $http.get(Constants.baseUrl + '/liststacks', JSON.stringify(payLoad), jsonConfig);
		}

		this.describeStackEvents = function(payLoad) {
			return $http.get(Constants.baseUrl + '/describestackevents', JSON.stringify(payLoad), jsonConfig);
		}

		this.listStackResources = function(payLoad) {
			return $http.get(Constants.baseUrl + '/liststackresources', JSON.stringify(payLoad), jsonConfig);
		}

		this.describeStackResource = function(payLoad) {
			return $http.get(Constants.baseUrl + '/describestackresource', JSON.stringify(payLoad), jsonConfig);
		}
	} ];
});