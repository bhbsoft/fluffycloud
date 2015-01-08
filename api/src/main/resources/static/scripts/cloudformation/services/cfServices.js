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
			var stackName = angular.isUndefined(payLoad) ? null : payLoad.stackName;
			return $http.get(Constants.baseUrl + '/liststacks', {
				params : {
					stackName : stackName,
				}
			});
		}

		this.describeStackEvents = function(payLoad) {
			var stackName = angular.isUndefined(payLoad) ? null : payLoad.stackName;
			return $http.get(Constants.baseUrl + '/describestackevents', {
				params : {
					stackName : stackName,
				}
			});
		}

		this.listStackResources = function(payLoad) {
			var stackName = angular.isUndefined(payLoad) ? null : payLoad.stackName;
			return $http.get(Constants.baseUrl + '/liststackresources', {
				params : {
					stackName : stackName,
				}
			});
		}

		this.describeStackResource = function(payLoad) {
			return $http.get(Constants.baseUrl + '/describestackresource', {
				params : {
					stackName : payLoad.stackName,
					logicalResourceId : payLoad.logicalResourceId
				}
			});
		}

		this.describeStackResources = function(payLoad) {
			var stackName = angular.isUndefined(payLoad) ? null : payLoad.stackName;
			return $http.get(Constants.baseUrl + '/describestackresources', {
				params : {
					stackName : stackName,
				}
			});
		}
	} ];
});