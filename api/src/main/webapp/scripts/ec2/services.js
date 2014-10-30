'use strict';

define([ 'angular', './services/constants', './services/ec2Services' ], function(angular, constants, ec2Services) {

	/* Services */
	var module = angular.module('fluffyCloud.ec2.services', []);

	module.value('Constants', constants);
	module.service('EC2SERVICE', ec2Services);

	return module;
});
