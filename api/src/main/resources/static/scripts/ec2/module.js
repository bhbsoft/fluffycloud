'use strict';

define([ 'angular', './controllers', './services', './directives', './ec2Constants' ], function(angular, controllers,
		services, directives, ec2Constants) {

	var module = angular.module('fluffyCloud.ec2', [ 'fluffyCloud.ec2.controllers', 'fluffyCloud.ec2.services',
			'fluffyCloud.ec2.directives' ]);
	module.value("EC2CONSTANTS", ec2Constants);
	return module;
});
