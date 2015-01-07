'use strict';

define([ 'angular', './services/constants', './services/cfServices' ], function(angular, constants, cfServices) {

	/* Services */
	var module = angular.module('fluffyCloud.cf.services', []);

	module.value('CFServiceConstants', constants);
	module.service('cfSERVICE', cfServices);
	return module;
});
