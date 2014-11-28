'use strict';

define([ 'angular', './services/constants', './services/ec2Services',
		'./services/modalService' ], function(angular, constants, ec2Services,
		modalServices) {

	/* Services */
	var module = angular.module('fluffyCloud.ec2.services', []);

	module.value('Constants', constants);
	module.service('EC2SERVICE', ec2Services);
	module.service('MODALSERVICE', modalServices);

	return module;
});
