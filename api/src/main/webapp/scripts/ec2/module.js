'use strict';

define([ 'angular', './controllers', './services', './directives' ], function(
		angular, controllers, services, directives) {

	var module = angular.module('fluffyCloud.ec2', [
			'fluffyCloud.ec2.controllers', 'fluffyCloud.ec2.services',
			'fluffyCloud.ec2.directives' ]);

	return module;
});
